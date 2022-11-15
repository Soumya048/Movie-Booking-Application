
// get id from local storage
let url = "http://localhost:8088/user/get-movie/1";



function getMovieData(url) {
    return fetch(url)
    .then(res => res.json())
    .then((res) => {
        return res;
    })
    .catch(err => console.log(err));
} 

let movieContainer = document.getElementById("movie-body-section");
let theatreContainer = document.getElementById("theatre-section");

async function displayMovieData() {
    const data = await getMovieData(url);
    console.log(data); 

    document.title = data.movieName;

    movieContainer.innerHTML = "";
    theatreContainer.innerHTML = "";

    movieContainer.innerHTML = `
    <div id="movie-card" class="container">
        <div class="movie-card-inner">
            <div class="movie-data-div">
                <div class="poster-div">
                    <div class="poster-img-div">
                        <img src="${data.posterUrl}" alt="Movie-Poster">
                    </div>
                    <div class="poster-lower-text">
                        In cinemas
                    </div>
                </div>
                <div class="title-div">
                    <div>
                        <h1>${data.movieName}</h1>
                    </div>
                    <div>
                        <span><i class="fa-solid fa-star fa-2x cl-pink-color"></i> <h2>9.2/10</h2> <h4> <span>79.1K ratings ></i></span></h4> </span> 
                    </div>
                    <div class="add-rating-div">
                        <div>
                            <h3>Add your rating & review</h3>
                            <h4>Your ratings matter</h4>
                        </div>
                        <div class="ratenow-div">
                            <button id="rate-now-btn">Rate Now</button>
                        </div>
                    </div>
                    <div class="quality-language-div">
                        <span><p>${data.quality}</p><p>Hindi</p></span> 
                    </div>
                    <div>
                        <p>${data.duration} • ${data.genre} • UA • ${data.dateOfRelease} </p>  
                    </div>
                    <div class="book-tickets-div">
                        <button id="book-tickets-btn" class="bg-pink-color">Book tickects</button>
                    </div>
                </div>
            </div>
            <div class="shear-div">
                <span><i class="fa-solid fa-share-nodes"></i> Share</span>
            </div>
        </div>
    </div>

    <div class="about-container">
        <div class="about-container-inner">
            <h2>About the movie</h2>
            <p>Three friends take a trek to the Everest Base Camp which becomes a personal, 
                emotional and spiritual journey while battling their physical limitations and discovering the true meaning of freedom.</p>
        </div>

        <div class="about-container-inner">
            <h2>Cast</h2>
            <p>Cast becomes </p>
            <p>becomes a personal</p>
            <p>becomes a dasdadasd</p>
        </div>

        <div class="about-container-inner">
            <h2>Top reviews</h2>
        </div>
    </div>
    
    
    `

    const bookBtn = document.getElementById("book-tickets-btn");
    bookBtn.addEventListener("click", displayTheatreData);

}
displayMovieData();
// console.log( movie);




function displayTheatreData() {
    movieContainer.innerHTML = "";
    theatreContainer.innerHTML = "";

    theatreContainer.innerHTML = `
    
    <h1> Theatre</h1>

    `;
}




var day = new Date();
console.log(day);

var nextDay = new Date(day);
nextDay.setDate(day.getDate()+1);
console.log(nextDay); 


