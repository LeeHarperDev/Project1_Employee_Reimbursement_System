export default class DOMCleaner {
    static clearElement = (element) => {
        while(element.children.length !== 0) {
            element.removeChild(element.firstChild);
        }
    }
}