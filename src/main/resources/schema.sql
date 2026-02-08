CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS student (
    student_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(150) NOT NULL,
    phone_number BIGINT NOT NULL,
    course_type VARCHAR(100) NOT NULL,
    course_domain VARCHAR(100) NOT NULL,
    batch_join_date DATE NOT NULL,

    created_at DATETIME,
    modified_at DATETIME,
    created_by VARCHAR(100),
    modified_by VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS fee_structure (
    fee_structure_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT UNIQUE,

    total_fees DOUBLE NOT NULL,
    paid_amount DOUBLE DEFAULT 0,
    pending_amount DOUBLE DEFAULT 0,

    CONSTRAINT fk_fee_structure_student
        FOREIGN KEY (student_id)
        REFERENCES student(student_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS fee_payment (
    payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fee_structure_id BIGINT NOT NULL,

    amount DOUBLE NOT NULL,
    payment_mode VARCHAR(50) NOT NULL,
    remarks VARCHAR(255),
    payment_date DATE NOT NULL,
    created_by VARCHAR(100),

    CONSTRAINT fk_fee_payment_structure
        FOREIGN KEY (fee_structure_id)
        REFERENCES fee_structure(fee_structure_id)
        ON DELETE CASCADE
);
