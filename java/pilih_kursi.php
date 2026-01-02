<?php
include 'koneksi.php';
session_start();

// Ambil data dari POST jika tersedia
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $_SESSION['id_jadwal'] = $_POST['id_jadwal'] ?? 0;
    $_SESSION['jam'] = $_POST['jam'] ?? '-';
    $_SESSION['id_studio'] = $_POST['id_studio'] ?? 0;
    $_SESSION['id_film'] = $_POST['id_film'] ?? 0;
}

$id_studio = $_SESSION['id_studio'] ?? 0;
$id_film = $_SESSION['id_film'] ?? 0;
$id_jadwal = $_SESSION['id_jadwal'] ?? 0;

// Validasi id_studio, id_jadwal, id_film
if (!is_numeric($id_studio) || $id_studio <= 0 || !is_numeric($id_jadwal) || $id_jadwal <= 0 || !is_numeric($id_film) || $id_film <= 0) {
    echo "<p>Data jadwal atau studio tidak valid! <a href='boxing-tiket.php'>Kembali</a></p>";
    exit;
}

// Ambil nama film
$film_result = $conn->query("SELECT judul FROM film WHERE id_film = $id_film");
$judul_film = ($film_result && $film_result->num_rows > 0) ? $film_result->fetch_assoc()['judul'] : '-';

// Ambil nama bioskop berdasarkan id_jadwal
$bioskop_result = $conn->query("SELECT b.nama_bioskop FROM jadwal_tayang j JOIN studio s ON j.id_studio = s.id_studio JOIN bioskop b ON s.id_bioskop = b.id_bioskop WHERE j.id_jadwal = $id_jadwal");
$nama_bioskop = ($bioskop_result && $bioskop_result->num_rows > 0) ? $bioskop_result->fetch_assoc()['nama_bioskop'] : '-';

$query = "SELECT nomor_kursi, baris, status FROM kursi WHERE id_studio = $id_studio ORDER BY baris, nomor_kursi";
$result = $conn->query($query);

// Ambil nama studio
$studio_nama = '-';
if ($id_studio) {
    $studio_result = $conn->query("SELECT nama_studio FROM studio WHERE id_studio = $id_studio");
    if ($studio_result && $studio_result->num_rows > 0) {
        $studio_nama = $studio_result->fetch_assoc()['nama_studio'];
    }
}

// Cek jika query kursi gagal
if (!$result) {
    die("Query kursi gagal: " . $conn->error);
}

// Ambil harga dari tipe_studio
$hargaResult = $conn->query("SELECT t.harga FROM studio s JOIN tipe_studio t ON s.id_tipe = t.id_tipe WHERE s.id_studio = $id_studio");
$harga = ($hargaResult && $hargaResult->num_rows > 0) ? $hargaResult->fetch_assoc()['harga'] : 0;

$kursi = [];
while ($row = $result->fetch_assoc()) {
    $kursi[$row['baris']][] = $row;
}
?>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Pilih Tempat Duduk - Studio <?= $id_studio ?></title>
    <link rel="stylesheet" href="pilih_kursi.css">
    <link href="https://fonts.googleapis.com/css2?family=Oxanium:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div id="seatPage" class="container-seat">
        <div class="seat-header">
            <button class="back-btn">
                <a href="boxing-tiket.php">Kembali</a>
            </button>
            <h1 class="pilih-kursi">PILIH TEMPAT DUDUK</h1>
        </div>

        <div class="seat-info">
            <h2 class="judul-movie" style="color: #ffd700;"><?= htmlspecialchars($judul_film) ?></h2>
            <div class="seat-info-tags">
                <span class="info-tag"><?= htmlspecialchars($studio_nama) ?></span>
                <span class="info-tag">Tanggal <?= date('d/m/Y') ?></span>
                <span class="info-tag"> <?= htmlspecialchars($nama_bioskop) ?></span>
                <span class="info-tag">Pukul <?= $_SESSION['jam'] ?? '-' ?></span>
            </div>

            <div class="legend">
                <div class="legend-item"><div class="legend-color legend-available"></div> <span>Tersedia</span></div>
                <div class="legend-item"><div class="legend-color legend-occupied"></div> <span>Tidak Tersedia</span></div>
                <div class="legend-item"><div class="legend-color legend-selected"></div> <span>Pilihan Anda</span></div>
                <div class="legend-item"><div class="legend-color legend-sweetbox"></div> <span>Sweet Box</span></div>
            </div>

            <form action="pembayaran.php" method="post">
                <input type="hidden" name="harga" value="<?= $harga ?>">
                <input type="hidden" name="id_studio" value="<?= $id_studio ?>">

                <div class="seat-map-container">
                    <div class="seat-grid">
                        <?php foreach ($kursi as $baris => $daftarKursi): ?>
                            <div class="seat-row">
                                <?php foreach ($daftarKursi as $k): ?>
                                    <?php
                                        $class = 'seat';
                                        if ($k['status'] === 'dipesan') $class .= ' occupied';
                                        elseif ($k['status'] === 'sweetbox') $class .= ' sweetbox';
                                        else $class .= ' available';
                                    ?>
                                    <label class="<?= $class ?>">
                                        <input type="checkbox" name="kursi[]" value="<?= $k['nomor_kursi'] ?>" <?= $k['status'] === 'dipesan' ? 'disabled' : '' ?>>
                                        <?= $k['nomor_kursi'] ?>
                                    </label>
                                <?php endforeach; ?>
                            </div>
                        <?php endforeach; ?>
                    </div>
                </div>

                <div class="screen">
                    <div class="screen-line"></div>
                    <div class="screen-text">LAYAR BIOSKOP</div>
                </div>

                <div class="bottom-bar">
                    <div class="bottom-left">
                        <a href="jadwal.php" class="back-btn-bottom">Kembali</a>
                        <div class="total-price">
                            <div class="total-label">TOTAL HARGA</div>
                            <div class="total-amount" id="totalPrice">Rp0</div>
                        </div>
                    </div>
                    <div class="bottom-right">
                        <div class="selected-seats">
                            <div class="seats-label">TEMPAT DUDUK</div>
                            <div class="seats-list"><small></small></div>
                        </div>
                        <button type="submit" class="order-btn">
                             RINGKASAN ORDER (<span id="orderCount">0</span>)
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
