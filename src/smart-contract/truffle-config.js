require('dotenv').config();
const HDWalletProvider = require('@truffle/hdwallet-provider');
const mnemonicPhrase = process.env.MNEMONIC;
const infuraProjectId = process.env.INFURA_PROJECT_ID;


module.exports = {
  networks: {
    development: {
      host: "127.0.0.1",
      port: 7545,
      network_id: "*",
      websocket: true
    },
    sepolia: {
      provider: () => new HDWalletProvider({
        mnemonic: {
          phrase: mnemonicPhrase
        },
        providerOrUrl: `https://sepolia.infura.io/v3/${infuraProjectId}`
      }),
      network_id: 11155111,
      gas: 8000000,
      gasPrice: 10000000000,
      confirmations: 2,
      timeoutBlocks: 200,
      skipDryRun: true
    }
  },

  mocha: {
    // timeout: 100000
  },

  compilers: {
    solc: {
      version: "0.8.19",
    }
  },
};
