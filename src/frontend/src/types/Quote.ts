export type Quote = {
  id: string;
  quoteText: string;
  author: string;
  origin: string;
  creationType: CreationType;
};

enum CreationType {
  "MANUAL",
  "SAVED",
  "GENERATED"
}
