    const slider = document.getElementById('imageSlider');
    const sliderContent = document.querySelector('.slider-content');

    const slideHeight = document.querySelector('.slide').clientHeight;

    function nextSlide() {
        sliderContent.style.transform = `translateY(-${slideHeight}px)`;
        setTimeout(() => {
            sliderContent.appendChild(sliderContent.firstElementChild);
            sliderContent.style.transition = 'none';
            sliderContent.style.transform = 'translateY(0)';
            setTimeout(() => {
                sliderContent.style.transition = 'transform 1s ease-in-out';
            });
        }, 1000);
    }

    setInterval(nextSlide, 2000);