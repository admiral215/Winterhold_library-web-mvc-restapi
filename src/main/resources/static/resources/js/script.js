(function(){
    addActionDeleteModal();
    addActionExtendModal();
    addActionReturnModal();
    addListnerCodeBook();
    // addActionDeleteModalCategory();
}())

function addActionDeleteModal(){
    $('.delete-button').click(function(event){
        event.preventDefault();
        let name = $(this).data('name')
        let id = $(this).data('id');
        $('.delete-form').attr('action', `/${name}/delete/` + id);
      })
}

function addActionExtendModal(){
    $('.extend-button').click(function(event){
        event.preventDefault();
        let name = $(this).data('name')
        let id = $(this).data('id');
        $('.extend-form').attr('action', `/${name}/extend/` + id);
      })
}

function addActionReturnModal(){
    $('.return-button').click(function(event){
        event.preventDefault();
        let name = $(this).data('name')
        let id = $(this).data('id');
        $('.return-form').attr('action', `/${name}/return/` + id);
      })
}

function addListnerCodeBook(){
    let book = document.querySelector('#code')
    book.addEventListener('change', (event)=> getExistCodeBook(book.value))
}

function getExistCodeBook(code){
    fetch(`http://localhost:8080/api/v1/books/check/${code}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok ' + response.statusText);
      }
      return response.json();
    })
    .then(data => {
        if(data){
            $('.valid-book').text("not available")
        } else{
        $('.valid-book').text("available")
        }
    })
    .catch(error => {
      console.error('There has been a problem with your fetch operation:', error);
    });
}

// function addActionDeleteModalCategory(){
//     $('.delete-button-category').click(function(event){
//         event.preventDefault();
//         let id = $(this).data('id');
//         $('.delete-form-category').attr('action', '/books/delete/' + id);
//       })
// };

