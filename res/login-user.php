
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cinema Login - Luxury Experience</title>
     <link href="https://fonts.googleapis.com/css2?family=Oxanium:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'oxanium',sans-serif;
            background-image: linear-gradient(rgba(0, 0, 0, 0.708), rgba(0, 0, 0, 0.735)),
    url(FOTOKU7.png);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: left;
            margin-left: 500PX;
            overflow: hidden;
            position: relative;
        }

        body::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="grain" patternUnits="userSpaceOnUse" width="100" height="100"><circle cx="50" cy="50" r="0.5" fill="%23ffffff" opacity="0.1"/><circle cx="20" cy="20" r="0.3" fill="%23ffffff" opacity="0.05"/><circle cx="80" cy="30" r="0.4" fill="%23ffffff" opacity="0.08"/><circle cx="30" cy="80" r="0.2" fill="%23ffffff" opacity="0.06"/></pattern></defs><rect width="100" height="100" fill="url(%23grain)"/></svg>');
            animation: float 20s ease-in-out infinite;
        }

        @keyframes float {
            0%, 100% { transform: translateY(0px); }
            50% { transform: translateY(-10px); }
        }

        
        .login-container {
            background: #001225;
            backdrop-filter: blur(20px);
            border-radius: 20px;
            padding: 40px;
            width: 450px;
            max-width: 90vw;
            height: 90vh;
            max-height: 90vh;
            overflow-y: auto;
            box-shadow: 
                0 25px 50px -12px rgba(0, 0, 0, 0.995),
                0 0 0 1px rgba(255, 255, 255, 0.491);
            position: relative;
            transform: translateY(0);
            transition: all 0.3s ease;
        }

        .login-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #ff6b6b, #ffd93d, #6bcf7f, #4ecdc4, #45b7d1, #96ceb4);
            background-size: 400% 400%;
            animation: gradientShift 3s ease infinite;
        }

        @keyframes gradientShift {
            0%, 100% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
        }

        .login-container:hover {
            transform: translateY(-5px);
            box-shadow: 
                0 35px 60px -12px rgba(0, 0, 0, 0.5),
                0 0 0 1px rgba(255, 255, 255, 0.2);
        }

        .logo {
            text-align: center;
            margin-bottom: 25px;
        }


        .logo p {
            color: #abaaaa;
            font-size: 14px;
            font-weight: 500;
            letter-spacing: 2px;
            text-transform: uppercase;
        }

        .form-group {
            margin-bottom: 20px;
            color: white;
            position: relative;
        }

        .form-title { /* masuk ke akun mu*/
            color: #ffd93d;
            text-align: center;
            margin-bottom: 30px;
            font-size: 1.8rem;
            font-weight: 600;
        }

        .form-group label { /*email dan pasword*/
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            font-size: 14px;
            letter-spacing: 0.5px;
        }

        .form-group input {
            width: 100%;
            padding: 15px 20px;
            color: rgb(168, 167, 167);
            border: 1.5px solid #ffd93d;
            border-radius: 12px;
            font-size: 16px;
            background: rgba(2, 13, 50, 0.075);
            transition: all 0.3s ease;
            outline: none;
        }

        .form-group input:focus {
            border-color: #ffd700;
            color: white;
            background: #001225;
            box-shadow: 0 0 0 4px rgba(66, 153, 225, 0.1);
            transform: translateY(-2px);
        }

        .form-group input::placeholder {
            color: #aaaaaa94;
            font-weight: 400;
        }

        .password-wrapper {
            position: relative;
        }

        .toggle-password {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: #dc1313;
            cursor: pointer;
            font-size: 18px;
            transition: color 0.3s ease;
        }

        .toggle-password:hover {
            color: #4299e1;
        }

        .forgot-password {
            text-align: right;
            margin-bottom: 25px;
        }

        .forgot-password a { /*lupa password*/
            color: #ffd700;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .forgot-password a:hover {
            color: #f9f8f5;
        }

        .login-btn, .register-btn { /*login atau masuk*/
            width: 100%;
            padding: 16px;
            background: linear-gradient(45deg, #ffd700, #fff);
            border: none;
            border-radius: 12px;
            color: #001225;
            font-size: 16px;
            font-weight: 700;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 1px;
            position: relative;
            overflow: hidden;
        }

        .login-btn::before, .register-btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
            transition: left 0.5s;
        }

        .login-btn:hover::before, .register-btn:hover::before {
            left: 100%;
        }

        .login-btn:hover, .register-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 15px 30px -5px rgba(66, 153, 225, 0.4);
        }

        .login-btn:active, .register-btn:active {
            transform: translateY(0);
        }

        .form-container {
            display: none;
        }

        .form-container.active {
            display: block;
        }

        .form-nav { /*bagian masuk atau daftar*/
            display: flex;
            margin-bottom: 25px;
            background: #001225;
            border-radius: 12px;
            padding: 4px;
        }

        .form-nav button {
            flex: 1;
            padding: 12px 20px;
            border: none;
            background: transparent;
            border-radius: 8px;
            font-weight: 600;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.3s ease;
            color: #ffd700;
        }

        .form-nav button.active {
            background: #ffd700;
            color: #001225;
            box-shadow: 0 4px 12px -2px rgba(190, 225, 66, 0.3);
        }


        .form-row {
            display: flex;
            gap: 15px;
        }

        .form-row .form-group {
            flex: 1;
        }

        .divider {
            text-align: center;
            margin: 25px 0;
            position: relative;
            color: #fcf7f7;
            font-size: 14px;
        }

        .divider::before {
            content: '';
            position: absolute;
            top: 50%;
            left: 0;
            right: 0;
            height: 1px;
            background: linear-gradient(90deg, transparent, #ddd, transparent);
        }

        .divider span {
            background: #001225;
            padding: 0 20px;
            position: relative;
            z-index: 1;
        }

        .social-login {
            display: flex;
            gap: 15px;
            margin-bottom: 20px;
        }

        .social-btn {
            flex: 1;
            padding: 12px;
            border: 2px solid #ffd700;
            font-family: 'oxanium', sans-serif;
            border-radius: 12px;
            background:#001225;
            color: white;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
        }

        .social-btn:hover {
            border-color: #ffd700;
            color: #001225;
            background:  linear-gradient(45deg, #ffd700,#fff);
            transform: translateY(-2px);
            box-shadow: 0 8px 20px -5px rgba(0, 0, 0, 0.1);
        }

        .signup-link {
            text-align: center;
            color: #9b9a9a;
            font-size: 14px;
        }

        .signup-link a {
            color: #ffd700;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
        }

        .signup-link a:hover {
            color: #fefeff;
        }

        @media (max-width: 480px) {
            .login-container {
                padding: 30px 25px;
                margin: 10px;
                height: 95vh;
                max-height: 95vh;
            }
            
            .logo h1 {
                font-size: 28px;
            }
        }

        .floating-elements {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            pointer-events: none;
            overflow: hidden;
        }

        .floating-elements::before,
        .floating-elements::after {
            content: '';
            position: absolute;
            border-radius: 50%;
            background: rgba(6, 217, 20, 0.05);
            animation: float 15s ease-in-out infinite;
        }

        .floating-elements::before {
            width: 200px;
            height: 200px;
            top: 10%;
            left: 10%;
            animation-delay: -5s;
        }

        .floating-elements::after {
            width: 150px;
            height: 150px;
            bottom: 20%;
            right: 10%;
            animation-delay: -10s;
        }

        .container-dsc {
            background-color: #fff;
            position: absolute;
            margin: 100px 100px 100px 100px;
            
        }
    </style>
</head>
<body>
    <div class="floating-elements"></div>
    <div class="container-dsc">
        <h1 class="hello">Hello</h1>
        <div class="descripsi">
            <p>
                Selamat datang di website Z-TIX Cinema<br>Buruan login dan nikmati fitur sepuasnya
            </p>
        </div>
        
    </div>
    <div class="login-container">
        <div class="logo">
            <img src="z-tix_gold_new-removebg-preview.png" alt="ztix" style="max-width: 150px;">
            <p>Premium Z-TIX Experience</p>
        </div>
        
        <div class="form-nav">
            <button type="button" class="nav-btn active" onclick="showForm('login')">Masuk</button>
            <button type="button" class="nav-btn" onclick="showForm('register')">Daftar</button>
        </div>
        
        <!-- Login Form -->
        <div id="loginForm" class="form-container active">
            <h2 class="form-title">Masuk ke Akun Anda</h2>
            <form onsubmit="handleLogin(event)">
                <div class="form-group">
                    <label for="loginEmail">Email atau Username</label>
                    <input type="text" id="loginEmail" name="email" placeholder="Masukkan email atau username Anda" required>
                </div>
                
                <div class="form-group">
                    <label for="loginPassword">Password</label>
                    <div class="password-wrapper">
                        <input type="password" id="loginPassword" name="password" placeholder="Masukkan password Anda" required>
                        <button type="button" class="toggle-password" onclick="togglePassword('loginPassword')">👁️</button>
                    </div>
                </div>
                
                <div class="forgot-password">
                    <a href="#" onclick="alert('Fitur lupa password akan segera tersedia!')">Lupa Password?</a>
                </div>
                
                <button type="submit" class="login-btn">Masuk</button>
            </form>
        </div>
        
        <!-- Register Form -->
        <div id="registerForm" class="form-container">
             <div style="text-align: center; margin-bottom: 25px;">
                <h2 class="form-title" style="margin-bottom: 10px;">🎬 Bergabung dengan Z-TIX Plus</h2>
                <p style="color: white; font-size: 0.95rem; line-height: 1.4;">
                    Dapatkan akses ke <strong style="color: #ffd700;">ribuan film terbaru</strong> dan nikmati pengalaman menonton yang tak terlupakan!
                </p>
            </div>
            <form onsubmit="handleRegister(event)">
                <div class="form-row">
                    <div class="form-group">
                        <label for="firstName">Nama Depan</label>
                        <input type="text" id="firstName" name="firstName" placeholder="Nama depan" required>
                    </div>
                    <div class="form-group">
                        <label for="lastName">Nama Belakang</label>
                        <input type="text" id="lastName" name="lastName" placeholder="Nama belakang" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="registerEmail">Email</label>
                    <input type="email" id="registerEmail" name="email" placeholder="Masukkan email Anda" required>
                </div>
                
                <div class="form-group">
                    <label for="phone">Nomor Telepon</label>
                    <input type="tel" id="phone" name="phone" placeholder="Masukkan nomor telepon Anda" required>
                </div>
                
                <div class="form-group">
                    <label for="registerPassword">Password</label>
                    <div class="password-wrapper">
                        <input type="password" id="registerPassword" name="password" placeholder="Minimal 8 karakter" required minlength="8">
                        <button type="button" class="toggle-password" onclick="togglePassword('registerPassword')">👁️</button>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="confirmPassword">Konfirmasi Password</label>
                    <div class="password-wrapper">
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Ulangi password Anda" required>
                        <button type="button" class="toggle-password" onclick="togglePassword('confirmPassword')">👁️</button>
                    </div>
                </div>
                <div class="benefits-section" style="background: #ebc80348; padding: 20px; border-radius: 12px; margin: 20px 0; border: 1px solid rgba(245, 158, 11, 0.3);">
                    <h4 style="color: #ffd700; margin-bottom: 15px; font-size: 1rem;">🎁 Keuntungan Member Z TIX Plus:</h4>
                    <ul style="color: white; font-size: 0.9rem; list-style: none; padding: 0;">
                        <li style="margin-bottom: 8px;">🎟️ Diskon tiket hingga 25%</li>
                        <li style="margin-bottom: 8px;">🍿 Promo khusus snack & minuman</li>
                        <li style="margin-bottom: 8px;">⭐ Priority booking untuk film premiere</li>
                        <li style="margin-bottom: 8px;">🎂 Voucher spesial di hari ulang tahun</li>
                        <li>📱 Notifikasi film terbaru & jadwal tayang</li>
                    </ul>
                </div>
                
                <div class="form-group">
                    <label for="birthDate">Tanggal Lahir</label>
                    <input type="date" id="birthDate" name="birthDate" required>
                </div>
                
                <button type="submit" class="register-btn">Daftar Sekarang</button>
            </form>
        </div>
        
        <div class="divider">
            <span>atau masuk dengan</span>
        </div>
        
        <div class="social-login">
            <button class="social-btn" onclick="alert('Login dengan Google akan segera tersedia!')">
                <img src="Google_Icons-09-512-removebg-preview.png" alt="google" style="max-width: 25px;">Google</button>
            <button class="social-btn" onclick="alert('Login dengan Facebook akan segera tersedia!')">
                <img src="fb1-removebg-preview.png" alt="fb" style="max-width: 25px;">Facebook</button>
        </div>
        
        <div class="signup-link" id="switchText">
            Belum punya akun? <a href="#" onclick="showForm('register')">Daftar sekarang</a>
        </div>
    </div>
    

    <script>


        function showForm(formType) {
            const loginForm = document.getElementById('loginForm');
            const registerForm = document.getElementById('registerForm');
            const navButtons = document.querySelectorAll('.nav-btn');
            const switchText = document.getElementById('switchText');
            
            // Reset all nav buttons
            navButtons.forEach(btn => btn.classList.remove('active'));
            
            if (formType === 'login') {
                loginForm.classList.add('active');
                registerForm.classList.remove('active');
                document.querySelector('.nav-btn:first-child').classList.add('active');
                switchText.innerHTML = 'Belum punya akun? <a href="#" onclick="showForm(\'register\')">Daftar sekarang</a>';
            } else {
                registerForm.classList.add('active');
                loginForm.classList.remove('active');
                document.querySelector('.nav-btn:last-child').classList.add('active');
                switchText.innerHTML = 'Sudah punya akun? <a href="#" onclick="showForm(\'login\')">Masuk sekarang</a>';
            }
        }

        function togglePassword(inputId) {
            const passwordInput = document.getElementById(inputId);
            const toggleBtn = passwordInput.nextElementSibling;
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                toggleBtn.textContent = '🙈';
            } else {
                passwordInput.type = 'password';
                toggleBtn.textContent = '👁️';
            }
        }

        function handleLogin(e) {
    e.preventDefault();
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    if (email && password) {
        const btn = document.querySelector('.login-btn');
        const originalText = btn.textContent;
        btn.textContent = 'Memproses...';
        btn.disabled = true;

        setTimeout(() => {
            alert(`Selamat datang di CINEPLEX! Login berhasil untuk: ${email}`);
            sessionStorage.setItem('isLoggedIn', 'true');
            sessionStorage.setItem('userEmail', email);
            window.location.href = '../../index.php';
        }, 2000);
    }
}


        function handleRegister(e) {
            e.preventDefault();
            const firstName = document.getElementById('firstName').value;
            const lastName = document.getElementById('lastName').value;
            const email = document.getElementById('registerEmail').value;
            const phone = document.getElementById('phone').value;
            const password = document.getElementById('registerPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const birthDate = document.getElementById('birthDate').value;
            
            // Validasi password
            if (password !== confirmPassword) {
                alert('Password dan konfirmasi password tidak sama!');
                return;
            }
            
            // Validasi umur (minimal 13 tahun)
            const today = new Date();
            const birth = new Date(birthDate);
            const age = today.getFullYear() - birth.getFullYear();
            const monthDiff = today.getMonth() - birth.getMonth();
            
            if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
                age--;
            }
            
            if (age < 13) {
                alert('Anda harus berusia minimal 13 tahun untuk mendaftar!');
                return;
            }
            
            // Simulasi loading
            const btn = document.querySelector('.register-btn');
            const originalText = btn.textContent;
            btn.textContent = 'Memproses...';
            btn.disabled = true;
            
            setTimeout(() => {
                alert(`Selamat ${firstName} ${lastName}! Akun Anda berhasil dibuat. Silakan cek email untuk verifikasi.`);
                btn.textContent = originalText;
                btn.disabled = false;
                
                // Pindah ke form login setelah registrasi berhasil
                showForm('login');
            }, 2500);
        }

        // Animasi input focus
        document.querySelectorAll('input').forEach(input => {
            input.addEventListener('focus', function() {
                this.parentElement.style.transform = 'scale(1.02)';
            });
            
            input.addEventListener('blur', function() {
                this.parentElement.style.transform = 'scale(1)';
            });
        });

        // Efek parallax mouse
        document.addEventListener('mousemove', function(e) {
            const container = document.querySelector('.login-container');
            const rect = container.getBoundingClientRect();
            const x = e.clientX - rect.left - rect.width / 2;
            const y = e.clientY - rect.top - rect.height / 2;
            
            const rotateX = (y / rect.height) * 5;
            const rotateY = -(x / rect.width) * 5;
            
            container.style.transform = `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) translateY(-5px)`;
        });

        document.addEventListener('mouseleave', function() {
            const container = document.querySelector('.login-container');
            container.style.transform = 'perspective(1000px) rotateX(0deg) rotateY(0deg) translateY(0px)';
        });

        // Validasi real-time untuk konfirmasi password
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const password = document.getElementById('registerPassword').value;
            const confirmPassword = this.value;
            
            if (confirmPassword && password !== confirmPassword) {
                this.style.borderColor = '#ef4444';
                this.style.boxShadow = '0 0 0 4px rgba(239, 68, 68, 0.1)';
            } else {
                this.style.borderColor = '#4299e1';
                this.style.boxShadow = '0 0 0 4px rgba(66, 153, 225, 0.1)';
            }
        });
    </script>
</body>
</html>