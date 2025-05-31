function deleteEntity(url, event) {
    event.preventDefault();
    event.stopPropagation(); // prevent any parent handlers

    if(confirm('Tem certeza que deseja realizar essa remoção?')) {
        fetch(url, {
            method: 'DELETE',
            headers: {
                'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if(response) {
                //alert('Usuário apagado com sucesso');
                window.location.reload();
            } else {
                alert("Erro ao tentar remover");
            }
        })
            .catch(error => {
                console.error('Error: ', error);
                alert("Erro ao tentar remover");
            });
    }
    return false;
}
