import { SERVER_API_URL } from "@/constants/api_urls";
import type { GeneratedQuote } from "@/types/GeneratedQuote";
import type { Quote } from "@/types/Quote";

export const getAllUserSavedQuotes = async (): Promise<Quote[] | null> => {
  const response = await fetch(SERVER_API_URL, { method: "GET" });

  if (response.status !== 200) {
    return null;
  }

  const quotes: Quote[] = await response.json();
  return quotes;
};

export const saveGeneratedQuote = async (generatedQuote: GeneratedQuote): Promise<Quote | null> => {
  const response = await fetch(SERVER_API_URL, {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    },
    body: JSON.stringify(generatedQuote)
  });

  if (response.status !== 200) {
    if (response.status == 400) {
      // TODO handle bad request
    }

    return null;
  }

  const savedQuote: Quote = await response.json();
  return savedQuote;
};
