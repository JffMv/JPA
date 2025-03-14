//const API_URL = `${window.location.origin}/properties`;
const API_URL = `https://jffmv.duckdns.org:8080/properties`;
async function fetchProperties() {
    const response = await fetch(API_URL);
    const properties = await response.json();
    const table = document.getElementById('propertyTable');
    table.innerHTML = '';

    properties.forEach(prop => {
        table.innerHTML += `
            <tr>
                <td>${prop.id}</td>
                <td>${prop.address}</td>
                <td>${prop.price}</td>
                <td>${prop.size}</td>
                <td>${prop.description}</td>
                <td>
                    <button class="btn btn-warning btn-sm me-2" onclick="editProperty(${prop.id}, '${prop.address}', ${prop.price}, ${prop.size}, '${prop.description}')">Edit</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteProperty(${prop.id})">Delete</button>
                </td>
            </tr>`;
    });
}

async function addOrUpdateProperty() {
    const propertyId = document.getElementById('propertyId').value;
    const property = {
        address: document.getElementById('address').value,
        price: document.getElementById('price').value,
        size: document.getElementById('size').value,
        description: document.getElementById('description').value
    };

    const method = propertyId ? 'PUT' : 'POST';
    const url = propertyId ? `${API_URL}/${propertyId}` : API_URL;

    await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(property)
    });

    resetForm();
    fetchProperties();
}

function editProperty(id, address, price, size, description) {
    document.getElementById('propertyId').value = id;
    document.getElementById('address').value = address;
    document.getElementById('price').value = price;
    document.getElementById('size').value = size;
    document.getElementById('description').value = description;
}

async function deleteProperty(id) {
    await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    fetchProperties();
}

function resetForm() {
    document.getElementById('propertyId').value = '';
    document.getElementById('address').value = '';
    document.getElementById('price').value = '';
    document.getElementById('size').value = '';
    document.getElementById('description').value = '';
}

fetchProperties();
