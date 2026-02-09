-- Add delivery tracking fields to orders table
ALTER TABLE orders
ADD COLUMN delivery_notes VARCHAR(2000),
ADD COLUMN delivery_url VARCHAR(500),
ADD COLUMN delivered_at TIMESTAMP,
ADD COLUMN approved_at TIMESTAMP;

-- Add mock payment tracking fields
ALTER TABLE orders
ADD COLUMN mock_payment_id VARCHAR(100),
ADD COLUMN mock_payment_status VARCHAR(20) DEFAULT 'PENDING';

-- Indexes for mock payment
CREATE INDEX idx_orders_mock_payment ON orders(mock_payment_id);
CREATE INDEX idx_orders_payment_status ON orders(mock_payment_status);
