CREATE TABLE workspaces (
                            id UUID PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            owner_id VARCHAR(255) NOT NULL,
                            created_at TIMESTAMP WITH TIME ZONE NOT NULL
);