// Define constants for allowed image types and DOM elements
const allowedImageTypes = ['image/jpg', 'image/png', 'image/jpeg'];
const bannerInput = document.getElementById("upload-banner");
const posterInput = document.getElementById("upload-poster");
const actorInput = document.getElementById("upload-actor");
const characterInput = document.getElementById("upload-character");

const bannerImage = document.getElementById("banner-img");
const posterImage = document.getElementById("poster-img");
const actorImage = document.getElementById("actor-avatar");
const characterImage = document.getElementById("character-avatar");

const genresSelect = document.getElementById("genres-select");
const selectedGenres = document.getElementById('selected-holder');
const actorList = document.getElementById('actor-holder');

// Fetch and populate genres
async function fetchAndPopulateGenres() {
    try {
        const response = await fetch(`http://${linkPath}:${port}/admin/getGenres`);
        const genres = await response.json(); // Parse response as JSON

        genres.data.sort((a, b) => a.name.localeCompare(b.name)); // Sort genres alphabetically

        genres.data.forEach(genre => {
            const option = document.createElement('option');
            option.value = genre.name;
            option.textContent = genre.name;
            genresSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching genres:', error);
    }
}

// Handle genre selection
genresSelect.addEventListener("change", function () {
    const selectedGenre = genresSelect.value;

    if(selectedGenre === "") return;

    // Remove the selected option from the dropdown
    Array.from(genresSelect.options).forEach((option, index) => {
        if (option.value === selectedGenre) {
            genresSelect.remove(index);
            const options = Array.from(genresSelect.options);            
            options.length === 1 ? genresSelect.options[0].text = "Out of genres" :  genresSelect.options[0].text = "Select the movie's genres";
            genresSelect.selectedIndex = 0;
        }
    });

    // Add selected genre to the list
    addGenreToList(selectedGenre);
});

// Add genre to the selected list
function addGenreToList(genre) {
    const genreTagHtml = `
        <div class="tag-container">
            <p class="tag-contain">${genre}</p>
            <button class="tag-delete"><i class="fa fa-times" aria-hidden="true"></i></button>
        </div>`;

    const listItem = document.createElement('li');
    listItem.innerHTML = genreTagHtml;

    const deleteButton = listItem.querySelector('.tag-delete');
    deleteButton.addEventListener('click', function () {
        // Re-add the genre to the select dropdown
        const option = document.createElement('option');
        option.value = genre;
        option.textContent = genre;
        genresSelect.appendChild(option);

        // Sort options alphabetically
        const options = Array.from(genresSelect.options);
        options.sort((a, b) => a.value.localeCompare(b.value));
        genresSelect.innerHTML = ''; // Clear the current options
        options.forEach(option => genresSelect.appendChild(option));

        // Ensure the first option remains as a placeholder
        if (genresSelect.options.length > 1) {
            genresSelect.options[0].textContent = "Select the movie's genres";
            genresSelect.options[0].disabled = true;
        }

        // Remove the genre from the list
        listItem.remove();
    });

    selectedGenres.appendChild(listItem);
}

// Handle file input changes for poster and banner images
posterInput.addEventListener('change', function (event) {
    handleFileInput(event, posterInput, posterImage);
});

bannerInput.addEventListener('change', function (event) {
    handleFileInput(event, bannerInput, bannerImage);
});

actorInput.addEventListener('change', function (event) {
    handleFileInput(event, actorInput, actorImage);
});

characterInput.addEventListener('change', function (event) {
    handleFileInput(event, characterInput, characterImage);
});

// Generic function to handle file input and display image
function handleFileInput(event, inputElement, imageElement) {
    const file = event.target.files[0];
    if (file) {
        if (!allowedImageTypes.includes(file.type)) {
            alert("Only jpg or png formats are allowed not " + file.type);
            return;
        }
        const reader = new FileReader();
        reader.onload = function (e) {
            imageElement.src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
}

// Handle adding cast
document.getElementById('add-cast').addEventListener('click', function (event) {
    event.preventDefault(); // Prevent form submission

    const actorName = document.querySelector('input[placeholder="Movie\'s Actor\'s Name"]').value;
    const characterName = document.querySelector('input[placeholder="Movie\'s Character\'s Name"]').value;

    const actorFile = actorInput.files[0];
    const characterFile = characterInput.files[0];

    if (!actorName || !characterName || !actorFile || !characterFile) {
        alert("Please fill in all fields and upload images.");
        return;
    }

    if (!allowedImageTypes.includes(actorFile.type) || !allowedImageTypes.includes(characterFile.type)) {
        alert("Only jpg or png formats are allowed for images.");
        return;
    }

    processFilesAndAddCast(actorFile, characterFile, actorName, characterName);
});

// Process files and add cast to the list
function processFilesAndAddCast(actorFile, characterFile, actorName, characterName) {
    const actorReader = new FileReader();
    const characterReader = new FileReader();

    actorReader.onload = function (e) {
        const actorImage = e.target.result;

        characterReader.onload = function (e) {
            const characterImage = e.target.result;

            const castItemHtml = `
                <div class="cast-container">
                    <div class="front face">
                        <img src="${actorImage}" alt="Actor Image"/>
                        <p>${actorName}</p>
                    </div>
                    <div class="back face">
                        <img src="${characterImage}" alt="Character Image"/>
                        <p>${characterName}</p>    
                    </div>
                </div>`;

            const listItem = document.createElement('li');
            listItem.innerHTML = castItemHtml;

            actorList.appendChild(listItem);
        }
        characterReader.readAsDataURL(characterFile);
    }
    actorReader.readAsDataURL(actorFile);
}

// Trigger file input dialogs
function triggerBannerInput() {
    bannerInput.click();
}

function triggerPosterInput() {
    posterInput.click();
}

function triggerActorInput() {
    actorInput.click();
}

function triggerCharacterInput() {
    characterInput.click();
}

// Initialize genres on page load
fetchAndPopulateGenres();
