function eliminarContacto(elemento){
    var ok = confirm('¿Estas seguro de eliminar el contacto?');

    if(ok){
    elemento.nextElementSibling.submit();
    }

    }