

-- Create Category table
CREATE TABLE category (
    category_id BIGSERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

-- Create Skill table
CREATE TABLE skill (
    id BIGSERIAL PRIMARY KEY,
    skill_name VARCHAR(255) NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_skill_category FOREIGN KEY (category_id)
        REFERENCES category(category_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Create UserModel table
CREATE TABLE user_model (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(255) NOT NULL,
    CONSTRAINT chk_user_type CHECK (user_type IN ('CONSUMER', 'PROVIDER', 'BOTH', 'ADMIN'))
);

-- Create UserSkill table
CREATE TABLE user_skill (
    user_skill_id BIGSERIAL PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    rate DOUBLE PRECISION NOT NULL,
    experience INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    service_mode VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    CONSTRAINT fk_user_skill_user FOREIGN KEY (user_id)
        REFERENCES user_model(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_user_skill_skill FOREIGN KEY (skill_id)
        REFERENCES skill(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT chk_service_mode CHECK (service_mode IN ('REMOTE', 'LOCAL'))
);

-- Create Orders table
CREATE TABLE orders (
    order_id BIGSERIAL PRIMARY KEY,
    consumer_id BIGINT NOT NULL,
    provider_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    description VARCHAR(1000) NOT NULL,
    agreed_price DOUBLE PRECISION NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP,
    completed_at TIMESTAMP,
    CONSTRAINT fk_order_consumer FOREIGN KEY (consumer_id)
        REFERENCES user_model(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_order_provider FOREIGN KEY (provider_id)
        REFERENCES user_model(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_order_skill FOREIGN KEY (skill_id)
        REFERENCES skill(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT chk_order_status CHECK (status IN ('PENDING', 'ACCEPTED',  'IN_PROGRESS','DELIVERED', 'COMPLETED', 'CANCELLED'))
);

-- Create indexes for better query performance
CREATE INDEX idx_skill_category ON skill(category_id);
CREATE INDEX idx_user_skill_user ON user_skill(user_id);
CREATE INDEX idx_user_skill_skill ON user_skill(skill_id);
CREATE INDEX idx_user_skill_active ON user_skill(is_active);
CREATE INDEX idx_order_consumer ON orders(consumer_id);
CREATE INDEX idx_order_provider ON orders(provider_id);
CREATE INDEX idx_order_skill ON orders(skill_id);
CREATE INDEX idx_order_status ON orders(status);
CREATE INDEX idx_order_created_at ON orders(created_at);
CREATE INDEX idx_user_model_username ON user_model(username);

-- Add unique constraint on username
ALTER TABLE user_model ADD CONSTRAINT uk_user_model_username UNIQUE (username);

-- Comments for documentation
COMMENT ON TABLE category IS 'Stores skill categories';
COMMENT ON TABLE skill IS 'Stores available skills in the marketplace';
COMMENT ON TABLE user_model IS 'Stores user information';
COMMENT ON TABLE user_skill IS 'Junction table linking users with their skills and pricing';
COMMENT ON TABLE orders IS 'Stores service orders between consumers and providers';

COMMENT ON COLUMN user_skill.rate IS 'Hourly or per-service rate set by provider';
COMMENT ON COLUMN user_skill.experience IS 'Years of experience in this skill';
COMMENT ON COLUMN user_skill.is_active IS 'Whether the user is actively offering this skill';
COMMENT ON COLUMN orders.agreed_price IS 'Final negotiated price for the service';