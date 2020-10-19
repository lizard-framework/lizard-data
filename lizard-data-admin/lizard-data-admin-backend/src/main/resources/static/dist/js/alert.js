function alertBasic(body, type) {
    Swal.fire({
        icon: type,
        title: body
    });
}

function alertTopEnd(body, type, timer) {
    Swal.fire({
        position: 'top-end',
        icon: type,
        title: body,
        timer: timer,
        showConfirmButton: false,
    });
}