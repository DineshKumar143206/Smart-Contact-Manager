console.log("delete alert");

async function deletecontact(id){

	Swal.fire({
	  title: "Do you want to delete the contact ?",
	  icon : "warning",
	  showCancelButton: true,
	  confirmButtonText: "Delete"
	  
	}).then((result) => {
	  /* Read more about isConfirmed, isDenied below */
	  if (result.isConfirmed) {
	    const url = `http://localhost:8080/user/contacts/delete/`+id;
		window.location.replace(url);
	  } 
	});
		
}