CREATE TABLE qa.quotes (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  quoteText VARCHAR(1000) NOT NULL,
  author VARCHAR(1000),
  origin VARCHAR(1000)
  creationType VARCHAR(100) NOT NULL
);