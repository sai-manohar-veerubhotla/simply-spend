

-- User table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       first_name TEXT NOT NULL,
                       last_name TEXT NOT NULL,
                       email_id TEXT NOT NULL UNIQUE,
                       age INT NOT NULL,
                       phone TEXT NOT NULL,
                       role TEXT NOT NULL,
                       password TEXT NOT NULL,
                       manager_name TEXT NOT NULL,
                       address TEXT NOT NULL,
                       tag TEXT NOT NULL
);

-- Order table
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        status TEXT NOT NULL,
                        reason TEXT NOT NULL,
                        comment TEXT NOT NULL,
                        user_id INT NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Item table
CREATE TABLE items (
                       id SERIAL PRIMARY KEY,
                       type TEXT NOT NULL,
                       name TEXT NOT NULL,
                       price INT NOT NULL,
                       description TEXT NOT NULL,
                       order_id INT NOT NULL,
                       FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);


-- Sample data for the users table
INSERT INTO users (first_name, last_name, email_id, age, phone, role, password, manager_name, address, tag)
VALUES
    ('John', 'Doe', 'john.doe@example.com', 30, '1234567890', 'MGR', 'password123', 'James Smith', '123 Elm St', 'Senior Manager'),
    ('Jane', 'Smith', 'jane.smith@example.com', 25, '9876543210', 'REPORTEE', 'jane123', 'John Doe', '456 Maple St', 'Junior Developer');

-- Sample data for the orders table
INSERT INTO orders (status, reason, comment, user_id)
VALUES
    ('REQUESTED', 'Need new hardware', 'Requesting a new laptop', 1),
    ('APPROVED', 'Software license', 'License for development tool approved', 2);

-- Sample data for the items table
INSERT INTO items (type, name, price, description, order_id)
VALUES
    ('HARDWARE', 'Laptop', 1000, 'Dell XPS 15', 1),
    ('SOFTWARE', 'Development Tool License', 200, 'License for IntelliJ IDEA', 2),
    ('STATIONARY', 'Notebook', 10, 'A4 sized notebook', 1);

