"use client";
import { useEffect, useState } from "react";
import { usePathname } from "next/navigation"; // Use usePathname instead of useRouter
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "./globals.css"
import SideBarComponent from "./components/sidebar/SideBarComponent";

export default function Layout({ children }) {
  const [isSidebarToggled, setIsSidebarToggled] = useState(false);
  const pathname = usePathname(); // Get current route path
  useEffect(() => {
    // Add or remove class on body based on sidebar toggle state
    if (isSidebarToggled) {
      document.body.classList.add("toggle-sidebar");
    } else {
      document.body.classList.remove("toggle-sidebar");
    }
  }, [isSidebarToggled]);

  const toggleSidebar = () => {
    setIsSidebarToggled((prev) => !prev);
  };

  // Define routes where header and sidebar should be hidden
  const hideHeaderSidebarRoutes = ["/components/pages-login"];
  const shouldHideHeaderSidebar = hideHeaderSidebarRoutes.includes(pathname);

  return (
    <html lang="en">
      <head>
        <meta charSet="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Hospital Management Dashboard</title>

        {/* Favicons */}
        <link rel="icon" href="/assets/img/favicon.png" />
        <link rel="apple-touch-icon" href="/assets/img/apple-touch-icon.png" />

        {/* Google Fonts */}
        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link
          href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700|Nunito:300,400,600,700|Poppins:300,400,500,600,700"
          rel="stylesheet"
        />

        {/* Vendor CSS */}
        {/* <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css" /> */}
        <link
          rel="stylesheet"
          href="/assets/vendor/bootstrap-icons/bootstrap-icons.css"
        />
        <link
          rel="stylesheet"
          href="/assets/vendor/boxicons/css/boxicons.min.css"
        />
        <link rel="stylesheet" href="/assets/vendor/quill/quill.snow.css" />
        <link rel="stylesheet" href="/assets/vendor/quill/quill.bubble.css" />
        <link
          rel="stylesheet"
          href="/assets/vendor/simple-datatables/style.css"
        />
        <link rel="stylesheet" href="assets/vendor/remixicon/remixicon.css" />
        <link
          rel="stylesheet"
          href="assets/vendor/simple-datatables/style.css"
        />

        {/* Main CSS */}
        <link rel="stylesheet" href="/assets/css/style.css" />
      </head>
      <body>
        {/* Header */}
        {/* Conditional Rendering: Hide Header & Sidebar on Login Page */}
        {!shouldHideHeaderSidebar && (
          <>
            <header className="header fixed-top d-flex align-items-center">
              <div className="d-flex align-items-center justify-content-between">
                <a href="/" className="logo d-flex align-items-center">
                  <img src="/assets/img/logo.png" alt="Logo" />
                  <span className="d-none d-lg-block">Medix360</span>
                </a>
                <i
                  className="bi bi-list toggle-sidebar-btn"
                  onClick={toggleSidebar}
                ></i>
              </div>
              <div className="search-bar">
                <form
                  className="search-form d-flex align-items-center"
                  method="POST"
                  action="#"
                >
                  <input
                    type="text"
                    name="query"
                    placeholder="Search"
                    title="Enter search keyword"
                  />
                  <button type="submit" title="Search">
                    <i className="bi bi-search"></i>
                  </button>
                </form>
              </div>
              <nav className="header-nav ms-auto">
                <ul className="d-flex align-items-center">
                  <li className="nav-item d-block d-lg-none">
                    <a className="nav-link nav-icon search-bar-toggle" href="#">
                      <i className="bi bi-search"></i>
                    </a>
                  </li>

                  <li className="nav-item dropdown">
                    <a
                      className="nav-link nav-icon"
                      href="#"
                      data-bs-toggle="dropdown"
                    >
                      <i className="bi bi-bell"></i>
                      <span className="badge bg-primary badge-number">
                        4
                      </span>{" "}
                    </a>

                    <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow notifications">
                      <li className="dropdown-header">
                        You have 4 new notifications
                        <a href="#">
                          <span className="badge rounded-pill bg-primary p-2 ms-2">
                            View all
                          </span>
                        </a>
                      </li>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li className="notification-item">
                        <i className="bi bi-exclamation-circle text-warning"></i>
                        <div>
                          <h4>Lorem Ipsum</h4>
                          <p>Quae dolorem earum veritatis oditseno</p>
                          <p>30 min. ago</p>
                        </div>
                      </li>

                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li className="notification-item">
                        <i className="bi bi-x-circle text-danger"></i>
                        <div>
                          <h4>Atque rerum nesciunt</h4>
                          <p>Quae dolorem earum veritatis oditseno</p>
                          <p>1 hr. ago</p>
                        </div>
                      </li>

                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li className="notification-item">
                        <i className="bi bi-check-circle text-success"></i>
                        <div>
                          <h4>Sit rerum fuga</h4>
                          <p>Quae dolorem earum veritatis oditseno</p>
                          <p>2 hrs. ago</p>
                        </div>
                      </li>

                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li className="notification-item">
                        <i className="bi bi-info-circle text-primary"></i>
                        <div>
                          <h4>Dicta reprehenderit</h4>
                          <p>Quae dolorem earum veritatis oditseno</p>
                          <p>4 hrs. ago</p>
                        </div>
                      </li>

                      <li>
                        <hr className="dropdown-divider" />
                      </li>
                      <li className="dropdown-footer">
                        <a href="#">Show all notifications</a>
                      </li>
                    </ul>
                  </li>

                  <li className="nav-item dropdown">
                    <a
                      className="nav-link nav-icon"
                      href="#"
                      data-bs-toggle="dropdown"
                    >
                      <i className="bi bi-chat-left-text"></i>
                      <span className="badge bg-success badge-number">
                        3
                      </span>{" "}
                    </a>

                    <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow messages">
                      <li className="dropdown-header">
                        You have 3 new messages
                        <a href="#">
                          <span className="badge rounded-pill bg-primary p-2 ms-2">
                            View all
                          </span>
                        </a>
                      </li>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li className="message-item">
                        <a href="#">
                          <img
                            src="assets/img/messages-1.jpg"
                            alt=""
                            className="rounded-circle"
                          />
                          <div>
                            <h4>Maria Hudson</h4>
                            <p>
                              Velit asperiores et ducimus soluta repudiandae
                              labore officia est ut...
                            </p>
                            <p>4 hrs. ago</p>
                          </div>
                        </a>
                      </li>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li className="message-item">
                        <a href="#">
                          <img
                            src="assets/img/messages-2.jpg"
                            alt=""
                            className="rounded-circle"
                          />
                          <div>
                            <h4>Anna Nelson</h4>
                            <p>
                              Velit asperiores et ducimus soluta repudiandae
                              labore officia est ut...
                            </p>
                            <p>6 hrs. ago</p>
                          </div>
                        </a>
                      </li>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li className="message-item">
                        <a href="#">
                          <img
                            src="assets/img/messages-3.jpg"
                            alt=""
                            className="rounded-circle"
                          />
                          <div>
                            <h4>David Muldon</h4>
                            <p>
                              Velit asperiores et ducimus soluta repudiandae
                              labore officia est ut...
                            </p>
                            <p>8 hrs. ago</p>
                          </div>
                        </a>
                      </li>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li className="dropdown-footer">
                        <a href="#">Show all messages</a>
                      </li>
                    </ul>
                  </li>

                  <li className="nav-item dropdown pe-3">
                    <a
                      className="nav-link nav-profile d-flex align-items-center pe-0"
                      href="#"
                      data-bs-toggle="dropdown"
                    >
                      <img
                        src="assets/img/profile-img.jpg"
                        alt="Profile"
                        className="rounded-circle"
                      />
                      <span className="d-none d-md-block dropdown-toggle ps-2">
                        K. Anderson
                      </span>{" "}
                    </a>

                    <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                      <li className="dropdown-header">
                        <h6>Kevin Anderson</h6>
                        <span>Web Designer</span>
                      </li>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li>
                        <a
                          className="dropdown-item d-flex align-items-center"
                          href="users-profile.html"
                        >
                          <i className="bi bi-person"></i>
                          <span>My Profile</span>
                        </a>
                      </li>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li>
                        <a
                          className="dropdown-item d-flex align-items-center"
                          href="users-profile.html"
                        >
                          <i className="bi bi-gear"></i>
                          <span>Account Settings</span>
                        </a>
                      </li>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li>
                        <a
                          className="dropdown-item d-flex align-items-center"
                          href="pages-faq.html"
                        >
                          <i className="bi bi-question-circle"></i>
                          <span>Need Help?</span>
                        </a>
                      </li>
                      <li>
                        <hr className="dropdown-divider" />
                      </li>

                      <li>
                        <a
                          className="dropdown-item d-flex align-items-center"
                          href="#"
                        >
                          <i className="bi bi-box-arrow-right"></i>
                          <span>Sign Out</span>
                        </a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </nav>
            </header>

            {/* Sidebar */}
         <SideBarComponent/>
          </>
        )}

        {/* Main Content */}
        <main id="main" className={`main ${shouldHideHeaderSidebar ? "login-main" : ""}`}>
          {children}
        </main>

        {/* Footer */}
        {!shouldHideHeaderSidebar && (
        <footer id="footer" className="footer">
          <div className="copyright">
            &copy; 2025 Hospital Management. All Rights Reserved.
          </div>
        </footer>
        )}
      </body>
    </html>
  );
}
