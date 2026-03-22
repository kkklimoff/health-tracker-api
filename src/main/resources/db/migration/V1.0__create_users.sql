CREATE TABLE IF NOT EXISTS USERS
(
    id            BIGSERIAL PRIMARY KEY,

    public_id     UUID UNIQUE         NOT NULL DEFAULT gen_random_uuid(),

    username      VARCHAR(50) UNIQUE  NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,

    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100),

    status        VARCHAR(50)         NOT NULL DEFAULT 'PENDING',

    last_login_at TIMESTAMPTZ,
    created_at    TIMESTAMPTZ         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMPTZ         NOT NULL DEFAULT CURRENT_TIMESTAMP
);