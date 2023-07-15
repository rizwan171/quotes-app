import { createApp } from "vue";
import App from "./App.vue";
import "./assets/css/index.css";
import { library } from "@fortawesome/fontawesome-svg-core";

import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import { faFloppyDisk, faCircleNotch } from "@fortawesome/free-solid-svg-icons";

library.add(faFloppyDisk, faCircleNotch);
createApp(App).component("font-awesome-icon", FontAwesomeIcon).mount("#app");
