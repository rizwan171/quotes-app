import { SERVER_API_URL } from "@/constants/api_urls";
import type { Quote } from "@/types/Quote";

export const getAllUserSavedQuotes = async (): Promise<Quote[] | null> => {
  const response = await fetch(SERVER_API_URL, {
    method: "GET",
    headers: {
      "Access-Control-Allow-Origin": "*"
    }
  });

  if (response.status !== 200) {
    return null;
  }

  const quotes: Quote[] = await response.json();
  return quotes;
};

export const saveQuote = async (quote: Quote): Promise<Quote | null> => {
  const response = await fetch(SERVER_API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(quote)
  });

  if (!response.ok) {
    if (response.status == 400) {
      // TODO handle bad request
    }

    return null;
  }

  const savedQuote: Quote = await response.json();
  return savedQuote;
};
