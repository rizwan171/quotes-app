import { createApp } from "vue";
import App from "./App.vue";
import "./assets/css/index.css";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faFloppyDisk, faCircleNotch, faTriangleExclamation, faCheck } from "@fortawesome/free-solid-svg-icons";
import Notifications from "@kyvg/vue3-notification";

library.add(faFloppyDisk, faCircleNotch, faTriangleExclamation, faCheck);
createApp(App).use(Notifications).component("font-awesome-icon", FontAwesomeIcon).mount("#app");
