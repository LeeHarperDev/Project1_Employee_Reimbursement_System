import Ticket from "./classes/Ticket.js"
import TicketList from "./classes/TicketList.js"
import DeterminationTypes from "./classes/DeterminationTypes.js"
import DOMCleaner from "./services/DOMCleaner.js";
import CookieProvider from "./classes/CookieProvider.js";
import Modal from "./modalHandler.js";

const ticketList = new TicketList();
const determinationTypes = new DeterminationTypes();

function init() {
    let ticketRequestForm = document.getElementById("reimbursementForm");
    
    loadTicketsElement();
    initiateSearchByType();

    ticketRequestForm.addEventListener("submit", sendTicketRequest);
}

async function loadTicketsElement(event = null) {
    CookieProvider.parseCookies();
    let modal = new Modal();
    let searchedEmployeeId = CookieProvider.getCookieValue("searchedEmployeeId");

    let res = await fetch(`/api/employees/${searchedEmployeeId}/tickets`);
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

function initiateSearchByType() {
    let searchElement = document.getElementById("searchByDetermination");
    determinationTypes.loadDeterminationTypes(searchElement);

    searchElement.addEventListener("change", loadTicketsElement);
}

function getEmployeeId() {
    let cookies = document.cookie.split('; ');
    let employeeId = -1;
    cookies.forEach((cookie) => {
        let parsedCookie = cookie.split('=');
        if (parsedCookie[0] === "employee_id") {
            employeeId = parseInt(parsedCookie[1]);
        } 
    });

    return employeeId;
}

async function sendTicketRequest(event) {
    event.preventDefault();

    let subject = document.getElementById("subject").value;
    let description = document.getElementById("description").value;

    let data = {
        subject: subject,
        description: description,
    }

    fetch (`/api/tickets/`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then((res) => {
        window.location.reload();
    });
}

window.addEventListener("load", init);