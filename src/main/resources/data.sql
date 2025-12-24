-- Sample Data for E-Commerce Application
-- This file is automatically executed on application startup

-- ============================================
-- CATEGORIES
-- ============================================
INSERT INTO categories (id, name, description) VALUES
(1, 'Electronics', 'Latest electronic gadgets and devices'),
(2, 'Fashion', 'Trendy clothing and accessories'),
(3, 'Home & Garden', 'Everything for your home and garden'),
(4, 'Sports & Outdoors', 'Sports equipment and outdoor gear'),
(5, 'Books', 'Books, magazines, and reading materials');

-- ============================================
-- PRODUCTS
-- ============================================
INSERT INTO products (id, name, description, price, stock_quantity, is_featured, category_id) VALUES
-- Electronics
(1, 'Wireless Bluetooth Headphones', 'Premium noise-cancelling wireless headphones with 30-hour battery life', 79.99, 50, true, 1),
(2, 'Smart Watch Pro', 'Fitness tracker with heart rate monitor and GPS', 199.99, 30, true, 1),
(3, 'USB-C Fast Charger', '65W fast charging adapter with multiple ports', 29.99, 100, false, 1),
(4, 'Portable Power Bank', '20000mAh high-capacity portable charger', 39.99, 75, false, 1),
(5, '4K Webcam', 'Ultra HD webcam with auto-focus and built-in microphone', 89.99, 40, true, 1),

-- Fashion
(6, 'Classic Denim Jacket', 'Vintage-style denim jacket for men and women', 59.99, 60, true, 2),
(7, 'Running Sneakers', 'Lightweight breathable running shoes', 69.99, 80, false, 2),
(8, 'Leather Wallet', 'Genuine leather bifold wallet with RFID protection', 34.99, 120, false, 2),
(9, 'Sunglasses UV Protection', 'Polarized sunglasses with UV400 protection', 44.99, 90, false, 2),
(10, 'Canvas Backpack', 'Durable canvas backpack with laptop compartment', 49.99, 70, true, 2),

-- Home & Garden
(11, 'Coffee Maker Deluxe', 'Programmable coffee maker with thermal carafe', 89.99, 45, true, 3),
(12, 'Indoor Plant Set', 'Set of 3 low-maintenance indoor plants with pots', 39.99, 55, false, 3),
(13, 'LED Desk Lamp', 'Adjustable LED lamp with USB charging port', 34.99, 85, false, 3),
(14, 'Throw Pillow Set', 'Set of 4 decorative throw pillows', 44.99, 100, false, 3),
(15, 'Kitchen Knife Set', 'Professional 8-piece stainless steel knife set', 79.99, 40, true, 3),

-- Sports & Outdoors
(16, 'Yoga Mat Premium', 'Non-slip eco-friendly yoga mat with carrying strap', 29.99, 110, false, 4),
(17, 'Camping Tent 4-Person', 'Waterproof camping tent with easy setup', 149.99, 25, true, 4),
(18, 'Resistance Bands Set', 'Set of 5 resistance bands with different strengths', 24.99, 95, false, 4),
(19, 'Water Bottle Insulated', 'Stainless steel insulated water bottle 32oz', 27.99, 130, false, 4),
(20, 'Bicycle Helmet', 'Adjustable safety helmet with LED light', 39.99, 65, false, 4),

-- Books
(21, 'The Art of Programming', 'Comprehensive guide to modern programming practices', 49.99, 50, true, 5),
(22, 'Cooking Masterclass', 'Professional cooking techniques and recipes', 34.99, 60, false, 5),
(23, 'Mindfulness Journal', 'Daily journal for meditation and self-reflection', 19.99, 80, false, 5),
(24, 'Business Strategy Guide', 'Essential strategies for business success', 44.99, 45, false, 5),
(25, 'Travel Photography', 'Tips and techniques for stunning travel photos', 39.99, 55, true, 5);

-- ============================================
-- USERS
-- ============================================
INSERT INTO users (id, username, email, password, full_name, address, phone_number) VALUES
(1, 'john_doe', 'john.doe@email.com', 'password123', 'John Doe', '123 Main St, New York, NY 10001', '+1-555-0101'),
(2, 'jane_smith', 'jane.smith@email.com', 'password123', 'Jane Smith', '456 Oak Ave, Los Angeles, CA 90001', '+1-555-0102'),
(3, 'bob_wilson', 'bob.wilson@email.com', 'password123', 'Bob Wilson', '789 Pine Rd, Chicago, IL 60601', '+1-555-0103'),
(4, 'alice_brown', 'alice.brown@email.com', 'password123', 'Alice Brown', '321 Elm St, Houston, TX 77001', '+1-555-0104'),
(5, 'charlie_davis', 'charlie.davis@email.com', 'password123', 'Charlie Davis', '654 Maple Dr, Phoenix, AZ 85001', '+1-555-0105');
