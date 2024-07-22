// Function to call the API

// Function to display the data
function displayData(data) {
    const contentDiv = document.getElementById('movie-display');
    console.log(data.data)
    const view = data.data.map(movie => {
        return `
        <div class="card" id=${movie.movie_id}>
            <div class="banner">
                <img src="${movie.banner}" alt="Movie Banner">
                <div class="rating">
                    <span class="circle">${movie.rate}%</span>
                </div>
            </div>
            <div class="poster">
                <img src="${movie.poster}" alt="Movie Poster">
            </div>
            <div class="details">
                <h1 class="name">${movie.title}</h1>
                <h2 class="release-date">Release Date: ${new Date(movie.release_date).toLocaleDateString()}</h2>
                <p class="desc">${movie.descr}</p>
                <p class="duration">Duration: ${Math.floor(movie.duration / 60)}h ${movie.duration % 60}min</p>
            </div>
        </div>
        `;
    }).join('');
    
    console.log(view);
    contentDiv.innerHTML = view;
}

async function fetchData() {
    try {
        const response = await fetch(`http://${linkPath}:${port}/admin/movie/list`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        displayData(data);
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// Call fetchData when the window loads
window.onload = function() {
    fetchData();
};
