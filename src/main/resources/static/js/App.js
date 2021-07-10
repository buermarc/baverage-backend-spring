class App {
    constructor(pageList) {
        this._pages = pageList;
        this._instances = [];

        //For performance reasons create an instance of each existing class / page which will be used for the whole session
        this._pages.forEach((page, index) => {
            this._instances[page.url] = new page.class(this);
        });
        //Load homepage on initial app creation
        this._handleRoute();
    }

    run() {
        //Listen for Hashchange --> Load the targeted page
        window.addEventListener("hashchange", () => {
            this._handleRoute();
        });
    }

    _handleRoute() {
        //remove # from location.hash
        let pageUrl = location.hash.slice(1);

        //If there is no hash use "/" to get to the homepage
        if(pageUrl == "") {
            pageUrl = "/";
        }

        /** Find pageUrl in pages array **/
        let page;
        let pageFound = this._pages.some((item, index) => {
            if(item.url == pageUrl) {
                page = item;
                return true;
            }
        });
        
        // let page = this._pages.find(p => matches = pageUrl.match(p.url));
        //Create new currentPageObj from selected class
        this.currentPageObj = this._instances[page.url];
        //Show content in content view
        this.currentPageObj.show();
    }

    //called by PageClass.show();
    //set new content in content view
    setPageContent(htmlContent) {
        $("#content").html(htmlContent);
    }
}