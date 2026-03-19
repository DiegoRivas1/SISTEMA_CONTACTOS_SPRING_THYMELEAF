document.addEventListener('DOMContentLoaded', () => {
    const navbar = document.querySelector('.navbar');
    const buttons = document.querySelectorAll('.navbar .btn');

    // Efecto matrix digital en hover
    buttons.forEach(btn => {
        btn.addEventListener('mouseenter', () => {
            // Crear lluvia digital
            createDigitalRain(btn);

            // Efecto de sonido visual
            //btn.style.animation = 'glitch 0.1s infinite';

            // Cambiar colores aleatoriamente
            const colors = ['#00ff9f', '#00b8ff', '#bd00ff', '#d600ff'];
            const randomColor = colors[Math.floor(Math.random() * colors.length)];
            btn.style.borderImage = `linear-gradient(45deg, ${randomColor}, ${colors[Math.floor(Math.random() * colors.length)]}) 1`;
        });

        btn.addEventListener('mouseleave', () => {
            btn.style.animation = '';
            btn.style.borderImage = '';
            removeDigitalRain(btn);
        });
    });

    // Efecto de escáner en navbar
    let scanLine = document.createElement('div');
    scanLine.className = 'scan-line';
    navbar.appendChild(scanLine);

    // Efecto de seguimiento del mouse cyberpunk
    navbar.addEventListener('mousemove', (e) => {
        const rect = navbar.getBoundingClientRect();
        const x = ((e.clientX - rect.left) / rect.width) * 100;
        const y = ((e.clientY - rect.top) / rect.height) * 100;

        navbar.style.background = `radial-gradient(circle at ${x}% ${y}%, 
            rgba(0, 255, 159, 0.2), 
            rgba(10, 10, 15, 0.95) 70%
        )`;
    });

    // Efecto de scroll cyberpunk
    window.addEventListener('scroll', () => {
        const scrollPercent = (window.scrollY / (document.documentElement.scrollHeight - window.innerHeight)) * 100;

        if (scrollPercent > 5) {
            navbar.style.background = `linear-gradient(90deg, 
                rgba(0, 255, 159, ${scrollPercent / 100}), 
                rgba(189, 0, 255, ${scrollPercent / 100})
            )`;
            navbar.style.backdropFilter = `blur(${10 + scrollPercent}px)`;
        } else {
            navbar.style.background = 'rgba(10, 10, 15, 0.95)';
            navbar.style.backdropFilter = 'blur(10px)';
        }
    });

    // Crear partículas cyberpunk
    createCyberParticles();
});

// Función para lluvia digital
function createDigitalRain(btn) {
    const rain = document.createElement('div');
    rain.className = 'digital-rain';
    rain.style.cssText = `
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        pointer-events: none;
        overflow: hidden;
        z-index: 0;
    `;

    for (let i = 0; i < 20; i++) {
        const drop = document.createElement('span');
        drop.style.cssText = `
            position: absolute;
            left: ${Math.random() * 100}%;
            top: -20px;
            color: ${getRandomCyberColor()};
            font-size: 10px;
            animation: rain ${1 + Math.random() * 2}s linear infinite;
            animation-delay: ${Math.random() * 2}s;
            opacity: 0.7;
            text-shadow: 0 0 5px currentColor;
        `;
        drop.textContent = Math.random() > 0.5 ? '1' : '0';
        rain.appendChild(drop);
    }

    btn.style.position = 'relative';
    btn.appendChild(rain);
    btn.dataset.rain = true;
}

function removeDigitalRain(btn) {
    const rain = btn.querySelector('.digital-rain');
    if (rain) rain.remove();
}

function getRandomCyberColor() {
    const colors = ['#00ff9f', '#00b8ff', '#001eff', '#bd00ff', '#d600ff'];
    return colors[Math.floor(Math.random() * colors.length)];
}

// Partículas cyberpunk
function createCyberParticles() {
    const container = document.createElement('div');
    container.className = 'cyber-particles';
    container.style.cssText = `
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        pointer-events: none;
        z-index: -1;
    `;

    for (let i = 0; i < 50; i++) {
        const particle = document.createElement('div');
        particle.style.cssText = `
            position: absolute;
            left: ${Math.random() * 100}%;
            top: ${Math.random() * 100}%;
            width: ${2 + Math.random() * 4}px;
            height: ${2 + Math.random() * 4}px;
            background: ${getRandomCyberColor()};
            box-shadow: 0 0 ${10 + Math.random() * 20}px currentColor;
            animation: floatParticle ${5 + Math.random() * 10}s linear infinite;
            opacity: ${0.3 + Math.random() * 0.5};
        `;
        container.appendChild(particle);
    }

    document.body.appendChild(container);
}


// Añadir estilos de animación faltantes
const style = document.createElement('style');
style.textContent = `
    @keyframes rain {
        0% { transform: translateY(0); }
        100% { transform: translateY(200px); }
    }
    
    @keyframes floatParticle {
        0% { transform: translate(0, 0) rotate(0deg); }
        33% { transform: translate(20px, -20px) rotate(120deg); }
        66% { transform: translate(-20px, 20px) rotate(240deg); }
        100% { transform: translate(0, 0) rotate(360deg); }
    }
    
    .scan-line {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 2px;
        background: linear-gradient(90deg, 
            transparent, 
            #00ff9f, 
            #bd00ff, 
            transparent
        );
        animation: scan 4s linear infinite;
        opacity: 0.5;
    }
    
    @keyframes scan {
        0% { top: -10%; }
        100% { top: 110%; }
    }
`;


document.head.appendChild(style);