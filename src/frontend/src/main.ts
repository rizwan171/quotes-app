import { createApp } from "vue";
import App from "./App.vue";
import "./assets/css/index.css";
import { library } from "@fortawesome/fontawesome-svg-core";

import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import { faFloppyDisk, faCircleNotch, faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";

library.add(faFloppyDisk, faCircleNotch, faTriangleExclamation);
createApp(App).component("font-awesome-icon", FontAwesomeIcon).mount("#app");
