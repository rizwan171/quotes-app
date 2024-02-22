import type { CreationType } from "@/enums/CreationType";

export type Quote = {
  id?: string;
  quoteText: string;
  author: string;
  origin: string;
  creationType: CreationType;
};