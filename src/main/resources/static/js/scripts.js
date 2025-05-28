function changeLanguage(lang) {
    localStorage.setItem('selectedLanguage', lang);
    const url = new URL(window.location);
    url.searchParams.set('lang', lang);
    window.location = url;
}

document.addEventListener('DOMContentLoaded', (event) => {
    const selectedLanguage = localStorage.getItem('selectedLanguage');
    if (selectedLanguage) {
        document.querySelector('.language-selector select').value = selectedLanguage;
    }
});