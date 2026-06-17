// $(document).ready(function () {
//     // Save item
//     document
//       .getElementById("submitItem")
//       .addEventListener("click", function () {
//         const itemCode = document.getElementById("itemCode").value;
//         const itemDescription = document.getElementById("itemDescription").value;
//         const itemPrice = document.getElementById("itemPrice").value;
//         const itemQty = document.getElementById("itemQty").value;
  
//         const itemData = {
//           code: itemCode,
//           description: itemDescription,
//           price: itemPrice,
//           qty: itemQty,
//         };
  
//         const itemJSON = JSON.stringify(itemData);
  
//         $.ajax({
//           url: "http://localhost:8080/app/item",
//           type: "POST",
//           data: itemJSON,
//           contentType: "application/json; charset=utf-8",
//           success: function (response) {
//             console.log("Result:", response);
//             const itemCode = response.code;
//             document.getElementById("itemCode").value = itemCode;
//           },
//           error: function (xhr, status, error) {
//             console.error("Error:", status, error);
//           },
//         });
//       });
//   });

//  // Update item
// document.getElementById('updateItem').addEventListener('click', function() {
//   const itemCode = document.getElementById('itemCode').value;
//   const itemDescription = document.getElementById('itemDescription').value;
//   const itemPrice = document.getElementById('itemPrice').value;
//   const itemQty = document.getElementById('itemQty').value;

//   const itemData = {
//       description: itemDescription,
//       price: itemPrice,
//       qty: itemQty
//   };

//   const itemJSON = JSON.stringify(itemData);

//   $.ajax({
//       url: `http://localhost:8080/app/item?code=${itemCode}`,
//       type: "PUT",
//       data: itemJSON,
//       contentType: "application/json; charset=utf-8",
//       success: function (response) {
//           console.log("Item updated successfully:", response);
//           // Optionally, refresh the item list or provide feedback to the user
//       },
//       error: function (xhr, status, error) {
//           console.error("Error:", status, error);
//       }
//   });
// });

// // Fetch and display item data
// function loadItems() {
//   $.ajax({
//       url: "http://localhost:8080/app/item",
//       type: "GET",
//       contentType: "application/json; charset=utf-8",
//       success: function (response) {
//           var itemTable = $('#itemTable');
//           itemTable.empty(); // Clear existing data
//           response.forEach(function(item) {
//               var row = '<tr class="new-row">' +
//                   '<td>' + item.code + '</td>' +
//                   '<td>' + item.description + '</td>' +
//                   '<td>' + item.price + '</td>' +
//                   '<td>' + item.qty + '</td>' +
//                   '</tr>';
//               itemTable.append(row);
//           });
//       },
//       error: function (xhr, status, error) {
//           console.error("Error:", status, error);
//       }
//   });
// }

// // Load items on page load
// loadItems();

// // Poll for new data every 5 seconds
// setInterval(loadItems, 5000);

// // Search item
// $("#searchItem").on("input", function () {
//   var searchValue = $(this).val().toLowerCase();
//   $("#itemTable tr").filter(function () {
//       $(this).toggle($(this).text().toLowerCase().indexOf(searchValue) > -1);
//   });
// });

// $("#searchItem").keypress(function (event) {
//   if (event.which == 13) {
//       var firstVisibleRow = $("#itemTable tr:visible").first();
//       if (firstVisibleRow.length > 0) {
//           var code = firstVisibleRow.find("td:nth-child(1)").text();
//           var description = firstVisibleRow.find("td:nth-child(2)").text();
//           var price = firstVisibleRow.find("td:nth-child(3)").text();
//           var qty = firstVisibleRow.find("td:nth-child(4)").text();
//           $("#itemCode").val(code);
//           $("#itemDescription").val(description);
//           $("#itemPrice").val(price);
//           $("#itemQty").val(qty);
//           $("#updateItem").data("code", code); // Store item code for update
//       }
//   }
// });

// // Delete item
// document.getElementById('deleteItem').addEventListener('click', function() {
//   const itemCode = document.getElementById('itemCode').value;
  
//   if (!itemCode) {
//       console.error("Item Code is required for deletion");
//       return;
//   }

//   $.ajax({
//       url: `http://localhost:8080/app/item?code=${itemCode}`,
//       type: "DELETE",
//       contentType: "application/json; charset=utf-8",
//       success: function (response) {
//           console.log("Item deleted successfully:", response);
//           // Optionally, clear the form and refresh the item list
//           $('#itemForm')[0].reset();
//           document.getElementById('itemCode').value = '';
//           loadItems();
//       },
//       error: function (xhr, status, error) {
//           console.error("Error:", status, error);
//       }
//   });
// });

// // Reset form
// document.getElementById('resetItem').addEventListener('click', function() {
//   $('#itemForm')[0].reset();
//   document.getElementById('itemCode').value = '';
// });
  
$(document).ready(function () {

  // ── Save item
  $('#submitItem').on('click', function () {
    const description = $('#itemDescription').val().trim();
    const price       = $('#itemPrice').val();
    const qty         = $('#itemQty').val();

    if (!description || !price || !qty) {
      alert('Please fill in all fields.');
      return;
    }

    $.ajax({
      url: 'http://localhost:8080/app/item',
      type: 'POST',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify({ description, price, qty }),
      success: function (response) {
        $('#itemCode').val(response.code);
        $('#itemCodeBadge').text(response.code);
        loadItems();
        if (window.refreshDashboard) window.refreshDashboard();
      },
      error: function (xhr, status, error) {
        console.error('Save error:', status, error);
        alert('Failed to save item.');
      }
    });
  });

  // ── Update item
  $('#updateItem').on('click', function () {
    const code        = $('#itemCode').val();
    const description = $('#itemDescription').val().trim();
    const price       = $('#itemPrice').val();
    const qty         = $('#itemQty').val();

    if (!code) { alert('Select an item to update.'); return; }

    $.ajax({
      url: `http://localhost:8080/app/item?code=${code}`,
      type: 'PUT',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify({ description, price, qty }),
      success: function () {
        loadItems();
      },
      error: function (xhr, status, error) {
        console.error('Update error:', status, error);
        alert('Failed to update item.');
      }
    });
  });

  // ── Delete item
  $('#deleteItem').on('click', function () {
    const code = $('#itemCode').val();
    if (!code) { alert('Select an item to delete.'); return; }
    if (!confirm('Delete this item?')) return;

    $.ajax({
      url: `http://localhost:8080/app/item?code=${code}`,
      type: 'DELETE',
      contentType: 'application/json; charset=utf-8',
      success: function () {
        resetItemForm();
        loadItems();
        if (window.refreshDashboard) window.refreshDashboard();
      },
      error: function (xhr, status, error) {
        console.error('Delete error:', status, error);
        alert('Failed to delete item.');
      }
    });
  });

  // ── Reset form
  $('#resetItem').on('click', resetItemForm);

  function resetItemForm() {
    $('#itemCode').val('');
    $('#itemDescription').val('');
    $('#itemPrice').val('');
    $('#itemQty').val('');
    $('#itemCodeBadge').text('New');
  }

  // ── Load & display items
  function loadItems() {
    $.ajax({
      url: 'http://localhost:8080/app/item',
      type: 'GET',
      contentType: 'application/json; charset=utf-8',
      success: function (response) {
        const tbody = $('#itemTable');
        tbody.empty();
        response.forEach(function (item) {
          tbody.append(`
            <tr class="new-row">
              <td>${item.code}</td>
              <td>${item.description}</td>
              <td>Rs. ${parseFloat(item.price).toFixed(2)}</td>
              <td>${item.qty}</td>
            </tr>
          `);
        });
      },
      error: function (xhr, status, error) {
        console.error('Load error:', status, error);
      }
    });
  }

  loadItems();
  setInterval(loadItems, 5000);

  // ── Search filter
  $('#searchItem').on('input', function () {
    const val = $(this).val().toLowerCase();
    $('#itemTable tr').filter(function () {
      $(this).toggle($(this).text().toLowerCase().includes(val));
    });
  });

  // ── Row click → fill form
  $(document).on('click', '#itemTable tr', function () {
    const cells = $(this).find('td');
    if (!cells.length) return;
    const priceRaw = cells.eq(2).text().replace('Rs. ', '');
    $('#itemCode').val(cells.eq(0).text());
    $('#itemDescription').val(cells.eq(1).text());
    $('#itemPrice').val(priceRaw);
    $('#itemQty').val(cells.eq(3).text());
    $('#itemCodeBadge').text(cells.eq(0).text());
  });

  // ── Search enter key
  $('#searchItem').on('keypress', function (e) {
    if (e.which === 13) {
      const first = $('#itemTable tr:visible').first();
      if (first.length) first.trigger('click');
    }
  });

});