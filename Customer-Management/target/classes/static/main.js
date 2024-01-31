token =getToken();
function getAllCustomers() {
    // Get the token from localStorage


    // Make a fetch request to get the list of customers
    fetch('http://localhost:8080/api/customers', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        }
    })
        .then(response => {
                console.log(response);
                return response.json();
        })
        .then(data => {

            updateCustomerTable(data);

        })

}


function updateCustomerTable(customers) {
    const tableBody = document.getElementById('customerTableBody');

    // Clear existing rows in the table body
    tableBody.innerHTML = '';

    // Iterate through the customers and append rows to the table body
    customers.forEach(customer => {
        const row = tableBody.insertRow();

        // Populate each cell with customer data
        row.insertCell(0).textContent = customer.id;
        row.insertCell(1).textContent = customer.firstName;
        row.insertCell(2).textContent = customer.lastName;
        row.insertCell(3).textContent = customer.email;
        row.insertCell(4).textContent = customer.phone;
        row.insertCell(5).textContent = customer.street;
        row.insertCell(6).textContent = customer.city;
        row.insertCell(7).textContent = customer.state;
        row.insertCell(8).textContent = customer.address;

        // Create buttons for update and delete actions
        const updateButton = document.createElement('button');
        updateButton.textContent = 'Update';
        updateButton.addEventListener('click', () =>
            updateCustomer(customer.id));
        row.insertCell(9).appendChild(updateButton);

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.addEventListener('click', () => DeleteCustomer(customer.id));
        row.insertCell(10).appendChild(deleteButton);
    });
}

function redirectToErrorPage(errorMessage) {
    // Redirect to error page with the error message
    const error = document.getElementById("errorhome");
    error.innerHTML=errorMessage;
}

function getToken()
{
    return 'Bearer '+localStorage.getItem("jwtToken");
}

function goToAdd()
{
    window.location.href="/web/add";
}

function Confirm(purpose)
{
    return window.confirm("Do you want to ${purpose} ");
}

function DeleteCustomer(customerId) {

    console.log(customerId);

        // Make a fetch request to get the list of customers
        fetch('http://localhost:8080/api/customers/'+customerId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getToken()
            }

        })
            .then(response => {
                if (response.status !== 204 && response.status !== 404 ) {
                    throw new Error(`Error: ${response.status} - ${response.statusText}`);
                }
                console.log(response);
                const d = document.getElementById("delete");
                d.textContent = "Deleted"
                window.location.href="/web/home"
            }).
            catch(error => {
            const d = document.getElementById("errorhome");
            d.textContent = error;
        });

}

function updateCustomer(customerId)
{
    window.location.href="/web/update/"+customerId;
}

function SyncCustomer()
{

    // Make a fetch request to get the list of customers
    fetch('http://localhost:8080/web/sync', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        }
    })
        .then(response => {
            console.log(response);
        })


}