// $(document).ready(function() {
//     // Navigation bar click event
//     $('.nav-link').click(function() {
//         var section = $(this).data('section');
//         $('.section').removeClass('active');
//         $('#' + section).addClass('active');
//     });
// });

$(document).ready(function () {

  const titles = {
    home: 'Dashboard', customer: 'Customers',
    item: 'Inventory', order: 'New Order', orderDetails: 'Order History'
  };

  window.navigateTo = function (section) {
    $('.section').removeClass('active');
    $('#' + section).addClass('active');
    $('.sn-nav-item').removeClass('active');
    $(`.sn-nav-item[data-section="${section}"]`).addClass('active');
    $('#pageTitle').text(titles[section] || '');
    $('#sidebar').removeClass('open');
  };

  $('.sn-nav-item').on('click', function (e) {
    e.preventDefault();
    const sec = $(this).data('section');
    if (sec) navigateTo(sec);
  });

  $('#mobileToggle').on('click', function () {
    $('#sidebar').toggleClass('open');
  });

  // Live clock
  function tick() {
    const d = new Date();
    const h = String(d.getHours()).padStart(2,'0');
    const m = String(d.getMinutes()).padStart(2,'0');
    $('#currentTime').text(h + ':' + m);
  }
  tick();
  setInterval(tick, 10000);

  // Dashboard stats
  window.refreshDashboard = function () {
    $.ajax({ url: 'http://localhost:8080/app/customer', type: 'GET',
      success: function (r) { $('#statCustomers').text(r.length); },
      error:   function ()  { $('#statCustomers').text('—'); }
    });
    $.ajax({ url: 'http://localhost:8080/app/item', type: 'GET',
      success: function (r) { $('#statItems').text(r.length); },
      error:   function ()  { $('#statItems').text('—'); }
    });
    $.ajax({ url: 'http://localhost:8080/app/orders', type: 'GET',
      success: function (r) {
        $('#statOrders').text(r.length);
        const rev = r.reduce(function (s, o) { return s + (o.subTotal || 0); }, 0);
        $('#statRevenue').text('Rs. ' + rev.toFixed(2));
      },
      error: function () { $('#statOrders').text('—'); $('#statRevenue').text('—'); }
    });
  };

  window.refreshDashboard();
});