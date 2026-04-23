package org.csagroup.actions;

public interface CSALocators {
	
	    //Landing page
		public String AcceptAll = "//button[@id='customise-cookies-all-cookies-btn']";
	    public String LoginRegister = "//*[@id='en_lang']//div[2]//div/div[2]/ul/li[2]/a";
	    		//"//div[contains(@class,'top-right-menu')]//a[contains(@class,'login-sales-new')]";
	    public String SecurityCode = "//input[@id='securitycode']";
	    public String SecurityCodeSubmitBtn = "//*[@id='securitycodesubmit']";
	    //Login Page 
	    public String Username = "//input[@id='emailField']";
	    public String Password ="//input[@id='passwordField']";
	    public String LoginBtn = "//input[@value='Login']";
	    public String ForgotPassword = "//*[text()='Forgot Your Password?']";
	    public String CreateAccount = "//*[@value='Create an Account']";
	    
	    // Search
	    public String SearchBox = "//input[@id='azure_suugestion']";
	    public String ProductTitles = "//*[@class='filter-product-card']//h2";
	    public String Filterlist = "//*[@class='dropdown-toggle--menu-list']";
	    
	    //Cart Locators
	    public String CartLink = "(//a[@class='header-right-link cart-header csa-cart-link cartcsa-icon csa-nav-link'])[2]";
	    public String ItemQuantity = "//*[@class='item-num-container']";
	    public String CheckoutBtn = "//*[@id='checkout_btn']";
	    public String Checkout_Continue ="//*[@id='shipForm']/div[5]/p/button";
	    public String ContinueBtn = "//*[@value='Continue']";
	    public String ShippingCost = "//*[@id='shippingMethod']/div[2]";
	    public String OrderView_ShippingCost = "//*[@id='shippingMethod']/div[2]";
	    public String OrderReViewContinueBtn = "//button[@id='processOrderReview']";
	    public String OrderView_HTSTax = "//*[@class='hst_tax']";
	    public String PaymentMethod_Total = "//*[@class='cc_footer']/span[2]";
	    public String PaymentMethodContinueBtn = "//*[@id='sopccPayment']";
	    public String PaymentMethod_EditCartBtn = "//*[@id='go-to-cart']";
	    public String LeaveCheckoutBtn = "//*[@id='confirm-go-to-cart']";
	    public String ClearCartBtn = "//button[@id='clearCart']";
	    public String ClearCartConfirmationBtn = "//button[@aria-label='Yes, remove item from cart']";
	    public String SubscirptionButton = "//button[2][@data-title='Subscriptions']";
	    public String PlusIcon = "(//*[@class='qnty-plus qty'])[3]";
	    public String OrderNumber = "//*[@class='thankyou-message']/b";
	    public String OrderSuccessMessage = "//h2[@class='panel-title cc_title heading-primary marginFixTitle']";
	    public String ViewCSAOnDemand = "//a[@aria-label='View in CSA OnDemand']";
	    
	    
	    //Create Account
	    public String CreateAccountBtn = "//*[@value='Create an Account']";
        public String Firstname = "//input[@id='firstName']";
        public String Lastname = "//input[@id='lastName']";
        public String CountryDropdown = "//select[@aria-label='Country-dropdown-selection']";
        public String StateDropdown = "//select[@aria-label='Province/State-selection']";
        public String IndustryDropdown = "//select[@aria-label='Industry-dropdown-selection']";
        public String Email ="//input[@name='username'] [@type='email']";
        public String NewPassword = "//input[@id='NewPassword']";
        public String ConfirmPassword = "//input[@id='ConfirmPassword']";
        public String TermsCheckbox = "//label[@class='cc_toc_flag_label']";
        public String PrivacyCheckbox ="//label[@class='cc_privacyAccept_flag_label']";
        public String CSAGroupCheckbox = "//label[@class='cc_emailOptIn_flag_label']";
        public String CreateAccountSubmitBtn = ".//*[@id='btn-registration-submit']";
        
        // CSA OnDemand Page
        public String OnDemandLink = "//*[@id='menu-store-primary-header-1']//a";
        public String CSAlogo = "//div[@class='header-logo']";
        public String CartIcon = "//div[@class='header-action cart-action']//a";
        public String LibrarySearchBox = "//input[@placeholder='Search Products']";
        public String DocumentLibraryLink = "//a[@aria-label='my-library']";
        public String SearchDocumentLibrary = "//*[@placeholder='Search Document Library']";
        public String DocumentLibraryItems = "//*[@class='document-product-parent']/div[1]";
        public String FavoriteHeartIcon = ".//*[@title='Add to Favourites']";
        public String DownloadPDFLink = "(.//*[@class='mylib-download-link'])[1]";
        public String ViewOnlineButton = "//*[@class='panel-collapse collapse mylib-collapse in']/div/div[1]/div/a[2]";
        public String CSAAdvantageLibrary ="//*[@data-aura-class='cCSAAdvantageLink']";
        public String OrderViewContinueButton ="//button[@id='free_placeOrder_top']";
        // Login credentials
        public String OnDemandUsername ="//input[@placeholder='Username']";
        public String OnDemandPassword= "//input[@placeholder='Password']";
        public String OnDemandLogin = "button[class*='uiButton']";
        public String OnDemandForgotPassword = "//a[text()='Forgot your password?']";
}
