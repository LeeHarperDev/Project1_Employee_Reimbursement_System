export default class TicketList {
    constructor() {
        this.tickets = [];
    }

    addTicket(ticket) {
        this.tickets.push(ticket);
    }
    
    getTicketsByDetermination = (determinationId) => {

        if (parseInt(determinationId) === -1) {
            return this.tickets;
        }  else if (parseInt(determinationId) === 0) {
            return this.tickets.filter((ticket) => {
                return ticket.determination === null;
            })
        } else {
            return this.tickets.filter((ticket) => {
                return (ticket.determination) ? ticket.determination.determinationType.id === parseInt(determinationId) : false;
            });
        }
    }

    getTickets = () => {
        return this.tickets;
    }
}