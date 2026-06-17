// $(document).ready(function() {
//     // Save customer
//     document.getElementById('submitCustomer').addEventListener('click', function() {
//         const customerName = document.getElementById('customerName').value;
//         const customerAddress = document.getElementById('customerAddress').value;
//         const customerSalary = document.getElementById('customerMobile').value;

//         const customerData = {
//             name: customerName,
//             address: customerAddress,
//             salary: customerSalary
//         };

//         const customerJSON = JSON.stringify(customerData);

//         $.ajax({
//             url: "http://localhost:8080/app/customer",
//             type: "POST",
//             data: customerJSON,
//             contentType: "application/json; charset=utf-8",
//             success: function (response) {
//                 console.log("Result :", response);
//                 const customerId = response.id;
//                 document.getElementById('customerId').value = customerId;
//             },
//             error: function (xhr, status, error) {
//                 console.error("Error:", status, error);
//             }
//         });
//     });

//     // Update customer
//     document.getElementById('updateCustomer').addEventListener('click', function() {
//         const customerId = document.getElementById('customerId').value;
//         const customerName = document.getElementById('customerName').value;
//         const customerAddress = document.getElementById('customerAddress').value;
//         const customerSalary = document.getElementById('customerMobile').value;

//         const customerData = {
//             name: customerName,
//             address: customerAddress,
//             salary: customerSalary
//         };

//         const customerJSON = JSON.stringify(customerData);

//         $.ajax({
//             url: `http://localhost:8080/app/customer?id=${customerId}`,
//             type: "PUT",
//             data: customerJSON,
//             contentType: "application/json; charset=utf-8",
//             success: function (response) {
//                 console.log("Customer updated successfully:", response);
//                 // Optionally, refresh the customer list or provide feedback to the user
//             },
//             error: function (xhr, status, error) {
//                 console.error("Error:", status, error);
//             }
//         });
//     });

//     // Fetch and display customer data
//     function loadCustomers() {
//         $.ajax({
//             url: "http://localhost:8080/app/customer",
//             type: "GET",
//             contentType: "application/json; charset=utf-8",
//             success: function (response) {
//                 var customerTable = $('#customerTable');
//                 customerTable.empty(); // Clear existing data
//                 response.forEach(function(customer) {
//                     var row = '<tr class="new-row">' +
//                         '<td>' + customer.id + '</td>' +
//                         '<td>' + customer.name + '</td>' +
//                         '<td>' + customer.address + '</td>' +
//                         '<td>' + customer.salary + '</td>' +
//                         '</tr>';
//                     customerTable.append(row);
//                 });
//             },
//             error: function (xhr, status, error) {
//                 console.error("Error:", status, error);
//             }
//         });
//     }

//     // Load customers on page load
//     loadCustomers();

//     // Poll for new data every 5 seconds
//     setInterval(loadCustomers, 5000);

//     // Search customer
//     $("#searchCustomer").on("input", function () {
//         var searchValue = $(this).val().toLowerCase();
//         $("#customerTable tr").filter(function () {
//             $(this).toggle($(this).text().toLowerCase().indexOf(searchValue) > -1);
//         });
//     });

//     $("#searchCustomer").keypress(function (event) {
//         if (event.which == 13) {
//             var firstVisibleRow = $("#customerTable tr:visible").first();
//             if (firstVisibleRow.length > 0) {
//                 var id = firstVisibleRow.find("td:nth-child(1)").text();
//                 var customerName = firstVisibleRow.find("td:nth-child(2)").text();
//                 var customerAddress = firstVisibleRow.find("td:nth-child(3)").text();
//                 var customerSalary = firstVisibleRow.find("td:nth-child(4)").text();
//                 $("#customerId").val(id);
//                 $("#customerName").val(customerName);
//                 $("#customerAddress").val(customerAddress);
//                 $("#customerMobile").val(customerSalary);
//                 $("#updateCustomer").data("id", id); // Store customer ID for update
//             }
//         }
//     });

//     // Delete customer
//     document.getElementById('deleteCustomer').addEventListener('click', function() {
//         const customerId = document.getElementById('customerId').value;
        
//         if (!customerId) {
//             console.error("Customer ID is required for deletion");
//             return;
//         }

//         $.ajax({
//             url: `http://localhost:8080/app/customer?id=${customerId}`,
//             type: "DELETE",
//             contentType: "application/json; charset=utf-8",
//             success: function (response) {
//                 console.log("Customer deleted successfully:", response);
//                 // Optionally, clear the form and refresh the customer list
//                 $('#customerForm')[0].reset();
//                 document.getElementById('customerId').value = '';
//                 loadCustomers();
//             },
//             error: function (xhr, status, error) {
//                 console.error("Error:", status, error);
//             }
//         });
//     });
    
//     // Reset form
//     document.getElementById('resetCustomer').addEventListener('click', function() {
//         $('#customerForm')[0].reset();
//         document.getElementById('customerId').value = '';
//     });
    
// });

$(document).ready(function () {

  // ── Save customer
  $('#submitCustomer').on('click', function () {
    const name    = $('#customerName').val().trim();
    const address = $('#customerAddress').val().trim();
    const salary  = $('#customerMobile').val().trim();

    if (!name || !address || !salary) {
      alert('Please fill in all fields.');
      return;
    }

    $.ajax({
      url: 'http://localhost:8080/app/customer',
      type: 'POST',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify({ name, address, salary }),
      success: function (response) {
        $('#customerId').val(response.id);
        $('#customerIdBadge').text(response.id);
        loadCustomers();
        if (window.refreshDashboard) window.refreshDashboard();
      },
      error: function (xhr, status, error) {
        console.error('Save error:', status, error);
        alert('Failed to save customer.');
      }
    });
  });

  // ── Update customer
  $('#updateCustomer').on('click', function () {
    const id      = $('#customerId').val();
    const name    = $('#customerName').val().trim();
    const address = $('#customerAddress').val().trim();
    const salary  = $('#customerMobile').val().trim();

    if (!id) { alert('Select a customer to update.'); return; }

    $.ajax({
      url: `http://localhost:8080/app/customer?id=${id}`,
      type: 'PUT',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify({ name, address, salary }),
      success: function () {
        loadCustomers();
      },
      error: function (xhr, status, error) {
        console.error('Update error:', status, error);
        alert('Failed to update customer.');
      }
    });
  });

  // ── Delete customer
  $('#deleteCustomer').on('click', function () {
    const id = $('#customerId').val();
    if (!id) { alert('Select a customer to delete.'); return; }
    if (!confirm('Delete this customer?')) return;

    $.ajax({
      url: `http://localhost:8080/app/customer?id=${id}`,
      type: 'DELETE',
      contentType: 'application/json; charset=utf-8',
      success: function () {
        resetCustomerForm();
        loadCustomers();
        if (window.refreshDashboard) window.refreshDashboard();
      },
      error: function (xhr, status, error) {
        console.error('Delete error:', status, error);
        alert('Failed to delete customer.');
      }
    });
  });

  // ── Reset form
  $('#resetCustomer').on('click', resetCustomerForm);

  function resetCustomerForm() {
    $('#customerId').val('');
    $('#customerName').val('');
    $('#customerAddress').val('');
    $('#customerMobile').val('');
    $('#customerIdBadge').text('New');
  }

  // ── Load & display customers
  function loadCustomers() {
    $.ajax({
      url: 'http://localhost:8080/app/customer',
      type: 'GET',
      contentType: 'application/json; charset=utf-8',
      success: function (response) {
        const tbody = $('#customerTable');
        tbody.empty();
        response.forEach(function (c) {
          tbody.append(`
            <tr class="new-row">
              <td>${c.id}</td>
              <td>${c.name}</td>
              <td>${c.address}</td>
              <td>${c.salary}</td>
            </tr>
          `);
        });
      },
      error: function (xhr, status, error) {
        console.error('Load error:', status, error);
      }
    });
  }

  loadCustomers();
  setInterval(loadCustomers, 5000);

  // ── Search filter
  $('#searchCustomer').on('input', function () {
    const val = $(this).val().toLowerCase();
    $('#customerTable tr').filter(function () {
      $(this).toggle($(this).text().toLowerCase().includes(val));
    });
  });

  // ── Row click → fill form
  $(document).on('click', '#customerTable tr', function () {
    const cells = $(this).find('td');
    if (!cells.length) return;
    $('#customerId').val(cells.eq(0).text());
    $('#customerName').val(cells.eq(1).text());
    $('#customerAddress').val(cells.eq(2).text());
    $('#customerMobile').val(cells.eq(3).text());
    $('#customerIdBadge').text(cells.eq(0).text());
  });

  // ── Search enter key
  $('#searchCustomer').on('keypress', function (e) {
    if (e.which === 13) {
      const first = $('#customerTable tr:visible').first();
      if (first.length) first.trigger('click');
    }
  });

});