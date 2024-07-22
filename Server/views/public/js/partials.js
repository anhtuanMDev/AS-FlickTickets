const asideElement = document.querySelector('aside');
const mainElement = document.querySelector('main');
const menu = document.querySelector("#menu-toggle");


function toggleNav() {

  // Get the computed style of the aside element
  const asideWidth = window.getComputedStyle(asideElement).width;

  // Check if the width is greater than 0
  if (parseInt(asideWidth) > 0) {
    // Close the sidebar
    menu.classList.remove("active");
    asideElement.style.width = "0";
    mainElement.style.marginLeft = "0";
  } else {
    // Open the sidebar
    menu.classList.add("active");
    asideElement.style.width = "250px";
    mainElement.style.marginLeft = "250px";
  }
}

function sidebarNavigate(path) {
  if (window.location.href == path) {
    return;
  }

  menu.classList.remove("active");
  asideElement.style.width = "0";
  mainElement.style.marginLeft = "0";
  console.log("click")

  const timeoutId = setTimeout(() => { window.location.href = path; clearTimeout(timeoutId); }, 700);

}