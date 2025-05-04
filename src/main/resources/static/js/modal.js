console.log("hey modal");

const viewcontactmodal = document.getElementById('default-modal');

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: "default-modal",
  override: true
};

const contactmodal = new Modal(viewcontactmodal, options, instanceOptions);

function opencontactmodal(){
	contactmodal.show();
}

function closecontactmodal(){
	contactmodal.hide();
}

async function loadcontactmodal(id) {
	console.log("Fetching contact with ID:", id);

	try{
		const data = await ( await fetch(`http://localhost:8080/api/contacts/${id}`)).json();
		console.log(data);
		
		document.querySelector("#contact_name").innerHTML = data.name;
		document.querySelector("#contact_email").innerHTML = data.email;
		document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
		document.querySelector("#contact_address").innerHTML = data.address;
		document.querySelector("#contact_description").innerHTML = data.description;
		document.querySelector("#contact_websitelink").innerHTML = data.websiteLink;
		document.querySelector("#contact_websitelink").href = data.websiteLink;
		document.querySelector("#contact_linkedinlink").innerHTML = data.linkedinLink;
		document.querySelector("#contact_linkedinlink").href = data.linkedinLink;
		document.querySelector("#contact_picture").src = data.picture;
		
		const contactFavorite = document.querySelector("#contact_favorite");
		if (data.favorite){
			contactFavorite.innerHTML = 
			"<i class = 'fas fa-star text-yellow-400'></i>"
		}
		else {
			contactFavorite.innerHTML = "Not Favorite Contact"
		}
		opencontactmodal();
	}
	catch (error) {
		console.log("Error occured: ", error);
	}
		
}



