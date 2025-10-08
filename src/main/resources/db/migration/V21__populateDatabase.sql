INSERT INTO categories (id, name) VALUES
(1, 'Fruits'),
(2, 'Vegetables'),
(3, 'Dairy'),
(4, 'Bakery'),
(5, 'Beverages');

INSERT INTO products (name, price, description, category_id) VALUES
-- Fruits
('Bananas', 0.59, 'Fresh yellow bananas, sold per lb.', 1),
('Gala Apples', 1.99, 'Crisp and sweet apples, perfect for snacks.', 1),
('Blueberries', 3.49, 'Fresh blueberries, 6 oz package.', 1),
('Avocados', 1.50, 'Ripe Hass avocados, sold individually.', 1),

-- Vegetables
('Broccoli', 1.29, 'Fresh green broccoli crown.', 2),
('Carrots', 0.89, 'Organic whole carrots, 1 lb bag.', 2),
('Spinach', 2.49, 'Fresh baby spinach, 5 oz.', 2),
('Tomatoes', 1.79, 'Vine-ripened tomatoes, sold per lb.', 2),

-- Dairy
('Whole Milk', 3.19, '1 gallon of whole milk.', 3),
('Greek Yogurt', 1.25, 'Plain Greek yogurt, 5.3 oz.', 3),
('Cheddar Cheese', 4.99, 'Sharp cheddar cheese block, 8 oz.', 3),
('Salted Butter', 3.49, 'Salted butter sticks, 1 lb.', 3),

-- Bakery
('White Bread', 2.29, 'Classic white sandwich bread, 20 oz loaf.', 4),
('Croissants', 4.59, 'Buttery croissants, pack of 4.', 4),
('Whole Wheat Bread', 2.49, 'Whole wheat loaf, 20 oz.', 4),
('Bagels', 3.99, 'Plain bagels, pack of 6.', 4),

-- Beverages
('Orange Juice', 4.29, 'Fresh squeezed orange juice, 52 oz.', 5),
('Green Tea', 2.99, 'Box of 20 green tea bags.', 5),
('Cola Soda', 1.25, '12 oz can of cola.', 5),
('Bottled Water', 0.99, 'Purified bottled water, 16.9 oz.', 5);
