console.log("hi");

let currentTheme =  getTheme();

document.addEventListener("DOMContentLoaded", () => { changeTheme() })

function changeTheme(){
	// set to web page
	changePageTheme(currentTheme, "");
	
	// set the listener to change theme button
	const tb = document.querySelector("#theme_button");
	
	
	tb.addEventListener("click", (event) => {
		let oldTheme = currentTheme;
		
		console.log("button clicked");
		
		if (currentTheme == "dark"){
			// dark to light
			currentTheme = "light";
		}
		else{
			// light to dark
			currentTheme = "dark";
		}
		
		changePageTheme(currentTheme, oldTheme); 
	});
}

// set theme to localstorage
function setTheme(theme){
	localStorage.setItem("theme", theme);
}

// get theme from localstorage
function getTheme(){
	let theme = localStorage.getItem("theme");
	if (theme)
		return theme
	else return "light";
	}
	
// change current page theme
function changePageTheme(theme, oldTheme){
	
	// updating in localStorage
	setTheme(currentTheme);
			
	// remove the current theme
	if (oldTheme){
	document.querySelector("html").classList.remove(oldTheme);
	}		
	
	// set the current theme
	document.querySelector("html").classList.add(theme);

	// change the text of the button
	document.querySelector("#theme_button").querySelector("span").textContent = theme == "light" ? "Dark" : "Light";

			
	
}
