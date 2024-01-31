token =getToken()
function addCustomer() {


    // Make a fetch request to get the list of customers
    fetch('http://localhost:8080/api/customers', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        }
        , body: getAllField()
    })
        .then(response => {
            if (response.status !== 201) {
                throw new Error(`Error: ${response.status} - ${response.statusText}`);
            }
            console.log(response);
            return response.json();
        })
        .then(data => {
            const d = document.getElementById("new");
            d.textContent = data.firstName +" "+data.lastName + " is added"
        }).catch(error => {
        const d = document.getElementById("new");
        d.textContent = error.message;
    });

}



function Return()
{
    window.location.href='/web/home'
}

function getAllField()
{
    const firstName = document.getElementById("firstname").value;
    const lastName = document.getElementById("lastname").value;
    const phone = document.getElementById("phone").value;
    const email = document.getElementById("email").value;
    const street = document.getElementById("street").value;
    const city = document.getElementById("city").value;
    const state = document.getElementById("state").value;
    const address = document.getElementById("address").value;
    return JSON.stringify({
        firstName,lastName,phone,email,street,city,state,address
    });
}

function getToken()
{
    return 'Bearer '+localStorage.getItem("jwtToken");
}


function EditCustomer()
{
    const customerId=document.getElementById("customerId").value;
    console.log(customerId);
    console.log(getAllField());

    // Make a fetch request to get the list of customers
    fetch('http://localhost:8080/api/customers/'+customerId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        }
        , body: getAllField()
    })
        .then(response => {
            if (response.status !== 200 && response.status !==404) {
                throw new Error(`Error: ${response.status} - ${response.statusText}`);
            }
            console.log(response);
            return response.json();
        })
        .then(data => {
            const d = document.getElementById("new");
            d.textContent = data.firstName +" "+data.lastName + " is updated"
        }).catch(error => {
        const d = document.getElementById("new");
        d.textContent = error.message;
    });

}