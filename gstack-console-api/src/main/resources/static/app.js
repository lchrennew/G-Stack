var stompClient = null;

function connect(uuid) {
    var socket = new SockJS('/gstack-console-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/specs/output/' + uuid, function (message) {
            var sout = $('#sout');
            if (message.body !== '!!!!!!') {
                sout.val(sout.val() + '\n' + message.body);
            }
            else
                disconnect()
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
        $('#sout').val('');
        $.get('/specs/execute?file=specs', function (response) {
            connect(response)
        })
    });
});

