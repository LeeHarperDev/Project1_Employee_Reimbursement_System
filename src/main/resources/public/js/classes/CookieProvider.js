export default class CookieProvider {
    static cookies = new Array();

    static parseCookies = () => {
        let newCookies = document.cookie.split('; ');

        console.log(CookieProvider.cookies);

        newCookies.forEach((cookie) => {
            let parsedCookie = cookie.split("=");
            CookieProvider.cookies[parsedCookie[0]] = parsedCookie[1];
        });
    }

    static getCookieValue(cookieKey) {
        return CookieProvider.cookies[cookieKey];
    }
}