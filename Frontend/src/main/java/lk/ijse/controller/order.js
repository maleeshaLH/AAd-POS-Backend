// $(document).ready(function() {
//     // Function to generate Order ID
//     function generateOrderId() {
//         const orderId = 'ORD-' + Math.floor(Math.random() * 1000000);
//         document.getElementById('orderId').value = orderId;
//     }

//     // Auto-fill Order Date with the current date
//     function fillOrderDate() {
//         const today = new Date().toISOString().split('T')[0];
//         document.getElementById('orderDate').value = today;
//     }

//     // Call the functions when the page loads
//     window.onload = function() {
//         generateOrderId();
//         fillOrderDate();
//     };

//     // Load customers and populate the dropdown
//     function loadCustomers() {
//         $.ajax({
//             url: "http://localhost:8080/app/customer",
//             type: "GET",
//             contentType: "application/json",
//             success: function(response) {
//                 var customerDropdown = $('#orderCustomerId');
//                 customerDropdown.empty();
//                 customerDropdown.append('<option selected>Select Customer ID</option>');
//                 response.forEach(function(customer) {
//                     customerDropdown.append(`<option value="${customer.id}">${customer.id}</option>`);
//                 });

//                 // When customer is selected, fill in the name
//                 customerDropdown.change(function() {
//                     var selectedCustomerId = $(this).val();
//                     var selectedCustomer = response.find(c => c.id === selectedCustomerId);
//                     $('#orderCustomerName').val(selectedCustomer ? selectedCustomer.name : '');
//                 });
//             },
//             error: function(xhr, status, error) {
//                 console.error("Error:", status, error);
//             }
//         });
//     }

//     // Load items and populate the dropdown
//     function loadItems() {
//         $.ajax({
//             url: "http://localhost:8080/app/item",
//             type: "GET",
//             contentType: "application/json",
//             success: function(response) {
//                 var itemDropdown = $('#selectItemCode');
//                 itemDropdown.empty();
//                 itemDropdown.append('<option selected>Select Item Code</option>');
//                 response.forEach(function(item) {
//                     itemDropdown.append(`<option value="${item.code}">${item.code}</option>`);
//                 });

//                 // When item is selected, fill in the name and price
//                 itemDropdown.change(function() {
//                     var selectedItemCode = $(this).val();
//                     var selectedItem = response.find(i => i.code === selectedItemCode);
//                     $('#selectItemName').val(selectedItem ? selectedItem.description : '');
//                     $('#selectItemPrice').val(selectedItem ? selectedItem.price : '');
//                 });
//             },
//             error: function(xhr, status, error) {
//                 console.error("Error:", status, error);
//             }
//         });
//     }

//     loadCustomers();
//     loadItems();

//     // Function to add selected item to the table
//     function addItemToTable() {
//         var itemCode = $('#selectItemCode').val();
//         var itemName = $('#selectItemName').val();
//         var itemPrice = $('#selectItemPrice').val();
//         var itemQty = $('#selectItemQty').val();

//         if (itemCode && itemName && itemPrice && itemQty) {
//             var table = $('#selectedItemsTable');
//             var newRow = `
//                 <tr>
//                     <td>${itemCode}</td>
//                     <td>${itemName}</td>
//                     <td>${itemPrice}</td>
//                     <td>${itemQty}</td>
//                 </tr>
//             `;
//             table.append(newRow);
//             updateTotal(); // Update total after adding item
//         } else {
//             alert('Please fill out all fields.');
//         }
//     }

//     // Event listener for the "Add" button
//     $('#addItem').click(function() {
//         addItemToTable();
//     });

//     // Function to reset the selected item fields
//     function resetSelectedItemFields() {
//         $('#selectItemCode').val('Select Item Code');
//         $('#selectItemName').val('');
//         $('#selectItemPrice').val('');
//         $('#selectItemQty').val('');
//     }

//     // Event listener for the "Reset" button
//     $('#resetSelectedItem').click(function() {
//         resetSelectedItemFields();
//     });

//     var selectedRow = null; 

//     // Function to handle row selection
//     $(document).on('click', '#selectedItemsTable tr', function() {
//         if (selectedRow) {
//             $(selectedRow).removeClass('table-active');
//         }
        
//         // Select the clicked row
//         selectedRow = this;
//         $(selectedRow).addClass('table-active');
//     });

//     // Function to remove the selected row
//     function removeSelectedItem() {
//         if (selectedRow) {
//             $(selectedRow).remove(); 
//             selectedRow = null; 
//             updateTotal(); // Recalculate total after removing an item
//         } else {
//             alert('Please select an item to remove.');
//         }
//     }

//     // Event listener for the "Remove" button
//     $('#removeSelectedItem').click(function() {
//         removeSelectedItem();
//     });

//     // Function to calculate and update total for all items
//     function updateTotal() {
//         let total = 0;

//         $('#selectedItemsTable tr').each(function() {
//             const itemPrice = parseFloat($(this).find('td').eq(2).text()) || 0;
//             const itemQty = parseFloat($(this).find('td').eq(3).text()) || 0;
//             total += itemPrice * itemQty;
//         });

//         $('#orderTotal').val(total.toFixed(2));
//         updateSubTotal(); // Update subtotal whenever total changes
//     }

//     // Function to calculate and update subtotal
//     function updateSubTotal() {
//         const total = parseFloat($('#orderTotal').val()) || 0;
//         const discountPercentage = parseFloat($('#orderDiscount').val()) || 0;
//         const discountAmount = (discountPercentage / 100) * total;
//         const subTotal = total - discountAmount;
//         $('#orderSubTotal').val(subTotal.toFixed(2));
//         updateBalance(); // Update balance whenever subtotal changes
//     }

//     // Function to calculate and update balance
//     function updateBalance() {
//         const subTotal = parseFloat($('#orderSubTotal').val()) || 0;
//         const cash = parseFloat($('#orderCash').val()) || 0;
//         const balance = cash - subTotal;
//         $('#orderBalance').val(balance.toFixed(2));
//     }

//     // Event listeners for input changes
//     $('#selectItemQty, #selectItemPrice').on('input', function() {
//         updateTotal();
//     });

//     $('#orderDiscount').on('input', function() {
//         updateSubTotal();
//     });

//     $('#orderCash').on('input', function() {
//         updateBalance();
//     });

//     // Event listener for the "Purchase" button
//     $('#submitOrder').click(function() {
//         const orderId = $('#orderId').val();
//         const orderDate = $('#orderDate').val();
//         const customerId = $('#orderCustomerId').val();
//         const total = $('#orderTotal').val();
//         const discount = $('#orderDiscount').val();
//         const subTotal = $('#orderSubTotal').val();
//         const cash = $('#orderCash').val();
//         const balance = $('#orderBalance').val();
        
//         let items = [];
//         $('#selectedItemsTable tr').each(function() {
//             const itemCode = $(this).find('td').eq(0).text();
//             const description = $(this).find('td').eq(1).text(); // Assuming description is in the second column
//             const price = $(this).find('td').eq(2).text();
//             const quantity = $(this).find('td').eq(3).text();
            
//             items.push({
//                 code: itemCode,
//                 description: description,
//                 price: parseFloat(price),
//                 qty: parseInt(quantity)
//             });
//         });

//         const orderData = {
//             orderId: orderId,
//             orderDate: orderDate,
//             customerId: customerId,
//             total: parseFloat(total),
//             discount: parseFloat(discount),
//             subTotal: parseFloat(subTotal),
//             cash: parseFloat(cash),
//             balance: parseFloat(balance),
//             items: items
//         };

//         $.ajax({
//             url: 'http://localhost:8080/app/orders',
//             type: 'POST',
//             contentType: 'application/json',
//             data: JSON.stringify(orderData),
//             success: function(response) {
//                 alert(response);
//                 location.reload(); // Reload the page to reset the form
//             },
//             error: function(xhr, status, error) {
//                 alert("Failed to place order: " + error);
//             }
//         });
//     });
// });

$(document).ready(function () {

  // ── Generate Order ID
  function generateOrderId() {
    $('#orderId').val('ORD-' + Math.floor(Math.random() * 1000000));
    $('#orderIdBadge').text($('#orderId').val());
  }

  // ── Auto-fill today's date
  function fillOrderDate() {
    $('#orderDate').val(new Date().toISOString().split('T')[0]);
  }

  generateOrderId();
  fillOrderDate();

  // ── Load customers dropdown
  function loadCustomers() {
    $.ajax({
      url: 'http://localhost:8080/app/customer',
      type: 'GET',
      contentType: 'application/json',
      success: function (response) {
        const dd = $('#orderCustomerId');
        dd.empty().append('<option value="">Select Customer</option>');
        response.forEach(function (c) {
          dd.append(`<option value="${c.id}">${c.id} — ${c.name}</option>`);
        });

        dd.off('change').on('change', function () {
          const sel = response.find(c => c.id === $(this).val());
          $('#orderCustomerName').val(sel ? sel.name : '');
        });
      },
      error: function () { console.error('Failed to load customers.'); }
    });
  }

  // ── Load items dropdown
  function loadItems() {
    $.ajax({
      url: 'http://localhost:8080/app/item',
      type: 'GET',
      contentType: 'application/json',
      success: function (response) {
        const dd = $('#selectItemCode');
        dd.empty().append('<option value="">Select Item</option>');
        response.forEach(function (item) {
          dd.append(`<option value="${item.code}">${item.code} — ${item.description}</option>`);
        });

        dd.off('change').on('change', function () {
          const sel = response.find(i => i.code === $(this).val());
          $('#selectItemName').val(sel ? sel.description : '');
          $('#selectItemPrice').val(sel ? sel.price : '');
        });
      },
      error: function () { console.error('Failed to load items.'); }
    });
  }

  loadCustomers();
  loadItems();

  // ── Add item to cart
  $('#addItem').on('click', function () {
    const code  = $('#selectItemCode').val();
    const name  = $('#selectItemName').val();
    const price = $('#selectItemPrice').val();
    const qty   = $('#selectItemQty').val();

    if (!code || code === 'Select Item' || !name || !price || !qty || qty <= 0) {
      alert('Please select an item and enter a valid quantity.');
      return;
    }

    $('#selectedItemsTable').append(`
      <tr>
        <td>${code}</td>
        <td>${name}</td>
        <td>${parseFloat(price).toFixed(2)}</td>
        <td>${qty}</td>
      </tr>
    `);

    // Reset item selector
    $('#selectItemCode').val('');
    $('#selectItemName').val('');
    $('#selectItemPrice').val('');
    $('#selectItemQty').val('');

    updateTotal();
  });

  // ── Row selection in cart
  let selectedRow = null;
  $(document).on('click', '#selectedItemsTable tr', function () {
    if (selectedRow) $(selectedRow).removeClass('table-active');
    selectedRow = this;
    $(this).addClass('table-active');
  });

  // ── Remove selected item
  $('#removeSelectedItem').on('click', function () {
    if (!selectedRow) { alert('Please select an item to remove.'); return; }
    $(selectedRow).remove();
    selectedRow = null;
    updateTotal();
  });

  // ── Reset item selector
  $('#resetSelectedItem').on('click', function () {
    $('#selectItemCode').val('');
    $('#selectItemName').val('');
    $('#selectItemPrice').val('');
    $('#selectItemQty').val('');
  });

  // ── Calculations
  function updateTotal() {
    let total = 0;
    $('#selectedItemsTable tr').each(function () {
      const price = parseFloat($(this).find('td').eq(2).text()) || 0;
      const qty   = parseFloat($(this).find('td').eq(3).text()) || 0;
      total += price * qty;
    });
    $('#orderTotal').val(total.toFixed(2));
    updateSubTotal();
  }

  function updateSubTotal() {
    const total    = parseFloat($('#orderTotal').val()) || 0;
    const discount = parseFloat($('#orderDiscount').val()) || 0;
    const subTotal = total - (discount / 100) * total;
    $('#orderSubTotal').val(subTotal.toFixed(2));
    updateBalance();
  }

  function updateBalance() {
    const subTotal = parseFloat($('#orderSubTotal').val()) || 0;
    const cash     = parseFloat($('#orderCash').val()) || 0;
    $('#orderBalance').val((cash - subTotal).toFixed(2));
  }

  $('#orderDiscount').on('input', updateSubTotal);
  $('#orderCash').on('input', updateBalance);

  // ── Submit order
  $('#submitOrder').on('click', function () {
    const orderId   = $('#orderId').val();
    const orderDate = $('#orderDate').val();
    const customerId= $('#orderCustomerId').val();
    const total     = parseFloat($('#orderTotal').val());
    const discount  = parseFloat($('#orderDiscount').val()) || 0;
    const subTotal  = parseFloat($('#orderSubTotal').val());
    const cash      = parseFloat($('#orderCash').val());
    const balance   = parseFloat($('#orderBalance').val());

    if (!customerId || customerId === 'Select Customer') {
      alert('Please select a customer.'); return;
    }
    if ($('#selectedItemsTable tr').length === 0) {
      alert('Please add at least one item.'); return;
    }
    if (!cash || cash <= 0) {
      alert('Please enter cash amount.'); return;
    }
    if (balance < 0) {
      alert('Insufficient cash.'); return;
    }

    let items = [];
    $('#selectedItemsTable tr').each(function () {
      items.push({
        code:        $(this).find('td').eq(0).text(),
        description: $(this).find('td').eq(1).text(),
        price:       parseFloat($(this).find('td').eq(2).text()),
        qty:         parseInt($(this).find('td').eq(3).text())
      });
    });

    $.ajax({
      url: 'http://localhost:8080/app/orders',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({ orderId, orderDate, customerId, total, discount, subTotal, cash, balance, items }),
      success: function (response) {
        alert('Order placed successfully!');
        if (window.refreshDashboard) window.refreshDashboard();
        location.reload();
      },
      error: function (xhr, status, error) {
        alert('Failed to place order: ' + error);
      }
    });
  });

  // ── Reset order form
  $('#resetOrder').on('click', function () {
    generateOrderId();
    fillOrderDate();
    $('#orderCustomerId').val('');
    $('#orderCustomerName').val('');
    $('#orderTotal, #orderDiscount, #orderSubTotal, #orderCash, #orderBalance').val('');
    $('#selectedItemsTable').empty();
  });

});