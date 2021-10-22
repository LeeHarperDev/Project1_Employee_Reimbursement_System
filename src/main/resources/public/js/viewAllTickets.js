import Ticket from "./classes/Ticket.js"
import TicketList from "./classes/TicketList.js"
import DeterminationTypes from "./classes/DeterminationTypes.js"
import DOMCleaner from "./services/DOMCleaner.js";
import Modal from "./modalHandler.js";

const ticketList = new TicketList();
const determinationTypes = new DeterminationTypes();

function init() {
    let determinationForm = document.getElementById("determinationForm");
    let determinationSelectElement = document.getElementById("determination_type");
    
    loadTicketsElement();
    initiateSearchByType();
    determinationTypes.loadDeterminationTypes(determinationSelectElement);

    determinationForm.addEventListener("submit", makeDeterminationRequest);
}

async function loadTicketsElement(event = null) {
    let modal = new Modal();

    let res = await fetch("/api/tickets");
    let data = await res.json();

    let ticketListElement = document.getElementById("ticketList");

    DOMCleaner.clearElement(ticketListElement);

    if (ticketList.getTickets().length === 0) {
        data.forEach((datum) => {
            let ticket = new Ticket(datum);
            ticketList.addTicket(ticket);
        });
    }

    if (event) {
        let tickets = ticketList.getTicketsByDetermination(event.target.value);

        tickets.forEach((ticket) => {
            ticket.displayTicketInElement(ticketListElement, true);
        });
    } else {
        let tickets = ticketList.getTickets();

        tickets.forEach((ticket) => {
            ticket.displayTicketInElement(ticketListElement, true);
        });
    }

    modal.loadButtons();
}

function makeDeterminationRequest(event) {
    event.preventDefault();

    let ticket_id = document.getElementById("ticket_id").value;
    let reimbursement_amount = document.getElementById("reimbursement_amount").value;
    let determination_type = document.getElementById("determination_type").value;

    let data = {
        ticket: {
            id: ticket_id
        },
        determinationType: {
          id: determination_type,
        },
        reimbursementAmount: reimbursement_amount,
    }

    fetch (`/api/tickets/${ticket_id}/close`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then((res) => {
        console.log(res.status);
        window.location.reload();
    });
}

function initiateSearchByType() {
    let searchElement = document.getElementById("searchByDetermination");
    determinationTypes.loadDeterminationTypes(searchElement);

    searchElement.addEventListener("change", loadTicketsElement);
}

window.addEventListener("load", init);