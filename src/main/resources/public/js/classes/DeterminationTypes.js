export default class DeterminationTypes {
    constructor() {
        
    }

    loadDeterminationTypes = async (element) => {
        if (!this.determinationTypes) {
            let res = await fetch("/api/determinations");
            let data = await res.json();
            this.determinationTypes = data;
        }

        this.displayDeterminationTypesInElement(element);
    }

    displayDeterminationTypesInElement = (element) => {
        this.determinationTypes.forEach((determinationType) => {
            let determinationOptionElement = document.createElement("option");
            determinationOptionElement.value = determinationType.id;
            let determinationOptionText = document.createTextNode(determinationType.name);

            determinationOptionElement.appendChild(determinationOptionText);
            element.appendChild(determinationOptionElement);
        });
    }
}