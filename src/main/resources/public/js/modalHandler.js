export default class Modal {
    constructor() {
        this.modalCloseButton = document.getElementById("closeModalButton");
        this.modelOpenButtons = document.getElementsByClassName("openModalBtn");

        document.getElementById("modal").style.display = "none";
    };

    loadButtons = () => {
        for (let i = 0; i < this.modelOpenButtons.length; i++) {
            this.modelOpenButtons[i].addEventListener("click", this.toggleModal);
        }
        this.modalCloseButton.addEventListener("click", this.toggleModal);
    };


    toggleModal = (event) => {
        let modalElement = document.getElementById("modal");
    
        if (modalElement.style.display == "none") {
            modalElement.style.display = "block";
        } else {
            modalElement.style.display = "none";
        }
    };
    
}