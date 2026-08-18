document.querySelector('#contactForm').addEventListener('submit', event => {
  event.preventDefault();
  const nombre = document.querySelector('#nombre').value.trim();
  const consulta = document.querySelector('#consulta').value.trim();
  window.open(`https://wa.me/50688041005?text=${encodeURIComponent(`Hola, soy ${nombre}. ${consulta}`)}`, '_blank', 'noopener');
});
