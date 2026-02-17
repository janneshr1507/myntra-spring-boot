INSERT INTO customer (customer_id, full_name, mobile_number, email, gender, customer_status, created_at, updated_at)
VALUES ('11111111-1111-1111-1111-111111111111', 'John Doe', '9876543210', 'john@example.com',
'MALE', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO vendor (vendor_id, name, created_at, updated_at)
VALUES ('22222222-2222-2222-2222-222222222222', 'Nike India', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO warehouse (warehouse_id, vendor_id, name, contact, email,
address1, address2, city, state, country, pincode, status, created_at, updated_at)
VALUES ('55555555-5555-5555-5555-555555555555', '22222222-2222-2222-2222-222222222222',
'Bangalore Central Warehouse', '9876543210', 'warehouse@nike.com', 'MG Road', 'Near Metro Station',
'Bangalore', 'Karnataka', 'India', '560001', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO items (item_id, vendor_id, warehouse_id, brand, model, color, item_size, fit, material,
actual_price, discount, selling_price, average_ratings, total_number_of_ratings, item_status, created_at, updated_at)
VALUES ('44444444-4444-4444-4444-444444444444', '22222222-2222-2222-2222-222222222222',
'55555555-5555-5555-5555-555555555555', 'Nike', 'Air Max 90', 'Black', 'M', 'Regular', 'Cotton',
5000.00, 0.10, 4500.00, 0.0, 0, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO inventory (inventory_id, item_id, available_qty, reserved_qty, sold_qty, created_at, updated_at)
VALUES ('66666666-6666-6666-6666-666666666666', '44444444-4444-4444-4444-444444444444',
100, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);







