export default class Ticket {
    constructor(ticket) {
        this.id = ticket.id;
        this.owner = ticket.owner;
        this.subject = ticket.subject;
        this.description = ticket.description;
        this.determination = ticket.determination;
    }

    displayTicketInElement = (containerElement, generateDeterminationBtn = false) => {
        let ticketContainerElement = document.createElement("div");
        ticketContainerElement.classList.add("ticket_card");
        let ticketHeaderElement = document.createElement("h2");
        let ticketHeaderSeperatorElement = document.createElement("hr");
        let ticketOwnerElement = document.createElement("span");
        let ticketDescriptionElement = document.createElement("p");
    
        let ticketHeader = document.createTextNode(`ID#${this.id}: ${this.subject}`);
        let ticketOwner = document.createTextNode(`Sent by: ${this.owner.employee.fname} ${this.owner.employee.lname}`)
        let ticketDescription = document.createTextNode(this.description);

        ticketDescriptionElement.appendChild(ticketDescription);
        ticketOwnerElement.appendChild(ticketOwner);
        ticketHeaderElement.appendChild(ticketHeader);
        ticketContainerElement.appendChild(ticketHeaderElement);
        this.generateDeterminationButton(ticketContainerElement, generateDeterminationBtn);
        ticketContainerElement.appendChild(ticketHeaderSeperatorElement);
        ticketContainerElement.appendChild(ticketOwnerElement);
        ticketContainerElement.appendChild(ticketDescriptionElement);

        ticketContainerElement.key = this.id;

        containerElement.appendChild(ticketContainerElement);
    }

    generateDeterminationButton = (ticketContainerElement, generateDeterminationBtn) => {
        if (this.determination == null) {
            let pendingDeterminationElement = document.createElement("span");
            pendingDeterminationElement.classList.add("pending");
            let pendingDetermination = document.createTextNode("PENDING");
            let makeDeterminationButton = document.createElement("button");

            if (generateDeterminationBtn) {
                makeDeterminationButton.classList.add("determination_btn");
                makeDeterminationButton.classList.add("openModalBtn");
                let makeDeterminationButtonText = document.createTextNode("Make Determination");
                makeDeterminationButton.appendChild(makeDeterminationButtonText);
            }
    
            pendingDeterminationElement.appendChild(pendingDetermination);
            ticketContainerElement.appendChild(pendingDeterminationElement);

            if (generateDeterminationBtn) {
                ticketContainerElement.appendChild(makeDeterminationButton);
            }
        } else {
            let determinationElement = document.createElement("span");
            determinationElement.classList.add(this.determination.determinationType.name.toLowerCase());
    
            let determinationText = null;
    
            if (this.determination.determinationType.name === "APPROVED") {
                determinationText = document.createTextNode(`APPROVED | AWARDED $${this.determination.reimbursementAmount}`);
            } else {
                determinationText = document.createTextNode(this.determination.determinationType.name.toUpperCase());
            }
    
            let managerDeterminedElement = document.createElement("p");
            let managerDetermined = document.createTextNode(`Resolved by: ${this.determination.manager.employee.fname} ${this.determination.manager.employee.lname}`);
    
            managerDeterminedElement.appendChild(managerDetermined);
    
            determinationElement.appendChild(determinationText);
            ticketContainerElement.appendChild(determinationElement);
            ticketContainerElement.appendChild(managerDeterminedElement);
        }
    }
}