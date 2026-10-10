# GitHub Copilot for Eclipse

## v0.21.0 (build 20261009)

#### 【中文】
因微软原版插件对本地大模型的支持不是太友好，仅仅ollama可用，而ollama又很鸡肋，故为支持更多本地大模型的使用，特此在微软开源基础上扩展了本地支持。
- **00_Diff** - 本地变更对应的代码变更差分

####
本地大模型的添加方式：
- 登录 GitHub → Preferences → GitHub Copilot → Model Management → **Add Provider...** 添加你的本地 OpenAI 兼容端点
- 选择你刚添加的本地 OpenAI 兼容端点后→ **Add Model...** 添加你的本地大模型信息

####
**手动编译完整步骤：**

```powershell or cmd
# 1. 拉取语言服务器二进制（必须，约1-2分钟；-f 不能省，
#    因为 package.json 声明了 5 个平台的包，不加 -f 会报 EBADPLATFORM）
cd com.microsoft.copilot.eclipse.core\copilot-agent
npm i -f

# 2. 回根目录完整打包（约8分钟）
cd ..\..
.\mvnw.cmd clean package
```

产物：
- **可安装 zip**：
  `com.microsoft.copilot.eclipse.repository\target\com.microsoft.copilot.eclipse.repository-0.21.0-SNAPSHOT.zip`
  （~500MB，含 5 平台语言服务器）
- Eclipse 里 Install New Software → Add → Archive 选这个 zip 即可

**环境要求**：JDK 17+、Node.js、Maven 用自带的 `mvnw.cmd` 即可。
- --
#### 【English】
Because Microsoft's original plugin has limited support for local LLMs — only Ollama is supported, and Ollama is quite limited in usefulness — this project extends the Microsoft open-source plugin to support more local LLMs.
- **00_Diff** - Code diff for the local changes

####
How to add a local LLM:
- Sign in to GitHub → Preferences → GitHub Copilot → Model Management → **Add Provider...** and add your local OpenAI-compatible endpoint
- After selecting the local OpenAI-compatible endpoint you just added, click **Add Model...** and add your local LLM information

####
**Full manual build steps:**

```powershell or cmd
# 1. Fetch the language server binaries (required, ~1-2 minutes; do not omit -f,
#    because package.json declares packages for 5 platforms; without -f it fails with EBADPLATFORM)
cd com.microsoft.copilot.eclipse.core\copilot-agent
npm i -f

# 2. Go back to the repo root and do a full package build (~8 minutes)
cd ..\..
.\mvnw.cmd clean package
```

Artifacts:

- **Installable zip**:
  `com.microsoft.copilot.eclipse.repository\target\com.microsoft.copilot.eclipse.repository-0.21.0-SNAPSHOT.zip`
  (~500MB, includes language servers for 5 platforms)
- In Eclipse, use Install New Software → Add → Archive and select this zip

**Requirements**: JDK 17+, Node.js, and Maven via the bundled `mvnw.cmd`.


=============================================================
## GitHub Copilot for Eclipse
GitHub Copilot for Eclipse brings AI-assisted coding to the Eclipse IDE with these core capabilities:

- **Code completions** for in-editor suggestions from code context or natural-language comments.
- **Next Edit Suggestions** provide context-aware suggestions for your next code edits.
- **Agent Mode** for conversational help and more autonomous, project-aware assistance.
- **Model Context Protocol (MCP)** integration to connect Copilot with external tools and services.
- **Advanced Agentic Capabilities** include Custom Agents, Isolated Subagents, and Plan Agent, with more agentic capabilities coming soon.

## Usage-based billing support

Starting from version **0.18.0**, we have added internal support for the upcoming [usage-based billing experience](https://github.blog/news-insights/company-news/github-copilot-is-moving-to-usage-based-billing/), including experience updates to the usage panel, usage notifications, and model picker. These changes will become visible once usage-based billing is rolled out.

To ensure compatibility with the new billing experience, we strongly recommend upgrading the plugin to **0.18.0 or later** as soon as possible.

Clients using older plugin versions will continue to function. However, the billing and usage experience may not be optimal and may not accurately reflect the latest usage-based billing experience.


## Getting access to GitHub Copilot

Sign up for [GitHub Copilot Free](https://github.com/settings/copilot?utm_source=vscode-chat-readme&utm_medium=second&utm_campaign=2025mar-em-MSFT-signup), or request access from your enterprise admin.

To use GitHub Copilot, an active subscription is required. Learn more about business and individual plans at [github.com/features/copilot](https://github.com/features/copilot?utm_source=vscode-chat&utm_medium=readme&utm_campaign=2025mar-em-MSFT-signup).

## Prerequisites

- [Eclipse IDE](https://www.eclipse.org/downloads/)
- An active [GitHub Copilot subscription](https://github.com/features/copilot)

## Install and set up

### Option 1: Eclipse Marketplace

1. Open [Eclipse Marketplace](https://marketplace.eclipse.org/) and go to the [GitHub Copilot plugin page](https://marketplace.eclipse.org/content/github-copilot).
2. Drag **Install** to your running Eclipse workspace.
3. Restart Eclipse.
4. Sign in to GitHub Copilot from Eclipse.

### Option 2: Install from update site

1. In Eclipse, open **Help → Install New Software…**
2. Add this update site URL: `https://azuredownloads-g3ahgwb5b8bkbxhd.b01.azurefd.net/github-copilot/`
3. Select **GitHub Copilot** and complete installation.
4. Restart Eclipse and sign in.

## Core capabilities

### Code completions

Inline suggestions (ghost text) appear as you type in the editor. Suggestions can range from small edits to multi-line changes.

### Next Edit Suggestions

Next Edit Suggestions predict your next edit location and propose the next change based on your recent edits and context.

### Agent and Ask Mode

**Ask Mode** provides conversational AI assistance for explaining code, generating code from requirements, suggesting refactors, and providing debugging guidance.

**Agent Mode** works autonomously across your project context to identify and fix issues, propose implementation steps, and support larger coding tasks with iterative guidance.

### Model Context Protocol (MCP)

MCP support enables integrating external tools and services into Copilot workflows where configured.

### Advanced Agentic Capabilities

- **Custom Agents** allow users to create personalized agents with specific instructions and behaviors.
- **Isolated Subagents** can be spawned by the main agent to handle specific tasks or contexts independently.
- **Plan Agent** can generate multi-step plans to accomplish complex tasks, breaking them down into manageable actions.
- **Skills** are reusable, specialized AI assistant templates that enrich chat context in Agent Mode. Skills are defined as `SKILL.md` files and can be scoped to a workspace or shared globally.

  - Creating Skills

    Place a `SKILL.md` file in any of these directories:

    - **Project-scoped:** `.github/skills/<skill-name>/`, `.claude/skills/<skill-name>/`, `.agents/skills/<skill-name>/`
    - **User-scoped (global):** `~/.copilot/skills/<skill-name>/`, `~/.claude/skills/<skill-name>/`, `~/.agents/skills/<skill-name>/`

    Each `SKILL.md` file can include YAML front matter with metadata (name, description) followed by Markdown content that provides domain knowledge, workflows, or instructions for the AI assistant.

    Skills are automatically discovered and available in Agent Mode. You can enable or disable skills in **Window → Preferences → Copilot → Chat → Enable Skills**.

For other available features in Eclipse, see the [Copilot feature matrix](https://docs.github.com/en/copilot/reference/copilot-feature-matrix?tool=eclipse).

## Privacy and responsible use

We follow responsible practices in accordance with our
[Privacy Statement](https://docs.github.com/en/site-policy/privacy-policies/github-privacy-statement).

To get the latest security fixes, please use the latest version of GitHub Copilot for Eclipse.

## Data and telemetry

The GitHub Copilot for Eclipse plugin collects usage data and sends it to Microsoft to help improve our products and services. Read our [privacy statement](https://privacy.microsoft.com/privacystatement) to learn more.

## Security

Please do not report security vulnerabilities in public issues.

See [SECURITY.md](SECURITY.md) for vulnerability reporting instructions.

## Support

For bug reports and feature requests, use this repository’s Issues.

For support guidance, see [SUPPORT.md](SUPPORT.md).

## Contributing

This project welcomes contributions and suggestions. Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details on how to get started, build the project, submit pull requests, and follow our code style guidelines.

Most contributions require you to agree to a Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us the rights to use your contribution. For details, visit [Contributor License Agreements](https://cla.opensource.microsoft.com).

## Trademarks

This project may contain trademarks or logos for projects, products, or services. Authorized use of Microsoft
trademarks or logos is subject to and must follow
[Microsoft's Trademark & Brand Guidelines](https://www.microsoft.com/legal/intellectualproperty/trademarks/usage/general).
Use of Microsoft trademarks or logos in modified versions of this project must not cause confusion or imply Microsoft sponsorship.
Any use of third-party trademarks or logos are subject to those third-party's policies.

## License

Copyright (c) Microsoft Corporation. All rights reserved.

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.